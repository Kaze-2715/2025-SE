package com.agri.platform.util.userRolePermission;

import com.agri.platform.interfaces.ICodeSender;
import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Async;

@Component("emailCodeSender")
@RequiredArgsConstructor
public class EmailCodeSender implements ICodeSender {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;
    @Async
    @Override
    public void send(String target, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(target);
        message.setSubject("注册验证码");
        message.setText("您的验证码是：" + code);
        mailSender.send(message);
    }
}
