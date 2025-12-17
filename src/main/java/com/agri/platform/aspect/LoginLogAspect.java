package com.agri.platform.aspect;

import java.time.LocalDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import com.agri.platform.controller.user.UserLoginController.ErrorResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

import com.agri.platform.controller.user.UserLoginController.LoginResponse;
import com.agri.platform.service.user.LoginLogService;
import com.agri.platform.entity.user.LoginLog;
import com.agri.platform.util.userRolePermission.WebUtil;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class LoginLogAspect {
    private final LoginLogService service;

    @Around("@annotation(com.agri.platform.annotation.LoginLog)")
    public Object record(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        LoginLog log = new LoginLog();
        log.setLoginIp(getIp(request));
        log.setUserAgent(request.getHeader("User-Agent"));
        log.setLoginTime(LocalDateTime.now());

        Object result = joinPoint.proceed();
        if (result instanceof ResponseEntity<?> resp &&
                resp.getBody() instanceof LoginResponse r) {
            log.setUserId(r.userId());
            log.setSuccess(true);
            log.setFailReason(null);
        } else if (result instanceof ResponseEntity<?> resp &&
                resp.getBody() instanceof ErrorResponse er) {
            // 2. 按失败解析
            log.setUserId(er.login());
            log.setSuccess(false);
            log.setFailReason(er.message());
        }
        service.save(log);
        return result;
    }

    private String getIp(HttpServletRequest request) {
        // String ip = request.getHeader("X-Forwarded-For");
        // if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        //     ip = request.getHeader("Proxy-Client-IP");
        // }
        // return ip.split(",")[0].trim();
        return WebUtil.getClientIp(request);
    }
}
