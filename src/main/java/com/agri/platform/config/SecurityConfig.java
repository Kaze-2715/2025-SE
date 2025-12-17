package com.agri.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. 关闭 HTTP Basic → 不再弹英文 Sign in
                .httpBasic().disable()
                // 2. 关闭 CSRF，方便前后端分离测试
                .csrf().disable()
                // 3. 静态资源放行（** 必须放在最后一段）
                .authorizeHttpRequests()
                .requestMatchers(
                        "/user-mgmt.html",
                        "/index.html",
                        "/js/**", // 合法：/js/** (** 在最后)
                        "/css/**", // 合法：/css/**
                        "/*.ico",
                        "/favicon.ico")
                .permitAll()
                // 其余请求先全部放行，由我们自己的 PermInterceptor 做细粒度鉴权
                .anyRequest().permitAll();
        return http.build();
    }
}