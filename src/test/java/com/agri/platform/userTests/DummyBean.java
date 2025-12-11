package com.agri.platform.userTests;

import com.agri.platform.annotation.Audit;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

/* ② 纯粹为了触发切面的“假”Bean */
@Component
@Slf4j
public class DummyBean {
    @Audit(operation = "虚拟操作", targetType = "DUMMY", targetId = "#result")
    public Long doSomething(String userId) {
        // 模拟主键
        System.out.println("<<<<< doSomething 被调用 >>>>>");
        System.out.println("<<<<< 注解类 = " + this.getClass().getSimpleName() + " >>>>>");
        return System.currentTimeMillis();
    }

    @PostConstruct
    public void init() {
        log.warn("<<<<< DummyBean 被 Spring 创建 >>>>>");
    }
}
