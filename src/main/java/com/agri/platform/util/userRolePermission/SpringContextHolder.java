package com.agri.platform.util.userRolePermission;

import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;

public class SpringContextHolder implements ApplicationContextAware{
    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) ctx.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return ctx.getBean(clazz);
    }
}
