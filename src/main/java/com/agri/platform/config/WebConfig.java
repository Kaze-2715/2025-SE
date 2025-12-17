package com.agri.platform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.agri.platform.util.userRolePermission.PermInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{
     @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PermInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/user-mgmt.html", // 用户中心页面
                        "/**/*.html", // 所有 html
                        "/**/*.js", // 所有脚本
                        "/**/*.css", // 所有样式
                        "/favicon.ico");
    }
}