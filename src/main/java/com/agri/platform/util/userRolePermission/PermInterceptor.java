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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!(handler instanceof HandlerMethod))
            return true;

        HandlerMethod hm = (HandlerMethod) handler;
        RequiredPermission anno = hm.getMethodAnnotation(RequiredPermission.class);
        if (anno == null)
            return true;

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(401);
            response.getWriter().write("未登录");
            return false;
        }

        @SuppressWarnings("unchecked")
        List<String> perms = (List<String>) session.getAttribute("perms");
        if (perms == null || !perms.contains(anno.value())) {
            response.setStatus(403);
            response.getWriter().write("无权限：" + anno.value());
            return false;
        }
        return true;
    }
}
