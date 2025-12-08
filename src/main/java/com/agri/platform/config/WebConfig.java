package com.agri.platform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.agri.platform.util.PermInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{
     @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PermInterceptor())
                .addPathPatterns("/api/**");
    }
}
