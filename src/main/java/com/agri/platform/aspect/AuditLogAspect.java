package com.agri.platform.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.aspectj.lang.reflect.MethodSignature;

import com.agri.platform.annotation.Audit;
import com.agri.platform.entity.log.AuditLog;
import com.agri.platform.mapper.log.AuditLogMapper;
import com.agri.platform.util.userRolePermission.GetUserIdFromSessionUtil;
import com.agri.platform.util.userRolePermission.WebUtil;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuditLogAspect {
    @Resource
    private HttpServletRequest request;
    @Resource
    private AuditLogMapper mapper;
    @Resource(name = "auditExecutor")
    private ThreadPoolTaskExecutor auditExecutor;

    private final SpelExpressionParser parser = new SpelExpressionParser();
    private final ParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    @Around("@annotation(audit)")
    public Object around(ProceedingJoinPoint joinPoint, Audit audit) throws Throwable {
        Object result = joinPoint.proceed();
        saveLog(joinPoint, audit, result);
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, Audit audit, Object result) {
        try {
            String targetId = audit.targetId().isBlank() ? resolveTargetId(joinPoint, result)
                    : evalSpel(joinPoint, result, audit.targetId());
            String userId = audit.userId().isBlank() ? GetUserIdFromSessionUtil.getCurrentUserId()
                    : evalSpel(joinPoint, result, audit.userId());
            String ip = audit.ip().isBlank() ? WebUtil.getClientIp(request)
                    : evalSpel(joinPoint, result, audit.ip());
            AuditLog log = new AuditLog();
            log.setUserId(userId);
            log.setTargetId(targetId);
            log.setTargetType(audit.targetType());
            log.setOperation(audit.operation());
            log.setIp(ip);

            auditExecutor.execute(() -> {
                try {
                    mapper.insertAuditLog(log);
                } catch (Exception e) {
                }
            });
        } catch (Exception ignoredException) {
        }
    }

    private String resolveTargetId(ProceedingJoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && (args[0] instanceof Long || args[0] instanceof String)) {
            return String.valueOf(args[0]);
        }
        return evalSpel(joinPoint, result, "result.data");
    }

    private String evalSpel(ProceedingJoinPoint joinPoint, Object result, String spel) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] names = nameDiscoverer.getParameterNames(signature.getMethod());
        Object[] args = joinPoint.getArgs();
        if (names != null) {
            for (int i = 0; i < names.length; i++) {
                context.setVariable(names[i], args[i]);
            }
        }
        context.setVariable("result", result);
        return parser.parseExpression(spel).getValue(context, String.class);
    }
}
