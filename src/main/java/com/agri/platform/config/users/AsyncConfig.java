package com.agri.platform.config.users;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import java.util.concurrent.Executor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class AsyncConfig {
    @Bean("logExecutor")
    public Executor logExecutor() {
        return new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200),
                r -> new Thread(r, "login-log-thread"), new ThreadPoolExecutor.CallerRunsPolicy());
        
    }
}
