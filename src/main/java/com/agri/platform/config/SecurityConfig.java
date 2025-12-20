package com.agri.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable()))
                // 1. 关闭 formLogin 和 httpBasic（都不会再弹英文登录框）
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // 2. 关闭 CSRF
                .csrf(AbstractHttpConfigurer::disable)

                // 3. 白名单放行
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/user-mgmt.html",
                                "/index.html",
                                "farm_owner2.html",
                                "tech_advisor2.html",
                                "/js/**",
                                "/css/**",
                                "/*.ico",
                                "/favicon.ico")
                        .permitAll()
                        // 其余请求全部先放行，由你自己的 PermInterceptor 做细粒度鉴权
                        .anyRequest().permitAll());
        return http.build();
    }
}