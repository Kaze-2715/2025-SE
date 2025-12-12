package com.agri.platform.util.userRolePermission;

import java.util.List;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.agri.platform.annotation.RequiredPermission;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PermInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod))
            return true;

        HandlerMethod hm = (HandlerMethod) handler;
        RequiredPermission anno = hm.getMethodAnnotation(RequiredPermission.class);
        if (anno == null)
            return true;

        // 只读，不创建会话
        HttpSession session = request.getSession(false);

        // ① 未登录 → 返回 JSON 401，**不带 Basic 挑战头**
        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            // 关键：不设置 WWW-Authenticate，浏览器就不会弹 Sign in 框
            response.getWriter().write("{\"code\":401,\"msg\":\"未登录\"}");
            return false;
        }

        // ② 已登录但缺权限 → JSON 403
        @SuppressWarnings("unchecked")
        List<String> perms = (List<String>) session.getAttribute("perms");
        if (perms == null || !perms.contains(anno.value())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":403,\"msg\":\"无权限：" + anno.value() + "\"}");
            return false;
        }

        return true;
    }
}
