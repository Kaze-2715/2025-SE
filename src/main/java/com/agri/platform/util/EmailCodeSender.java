package com.agri.platform.util;

import com.agri.platform.interfaces.ICodeSender;

import org.apache.ibatis.javassist.Loader.Simple;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Async;

@Component("emailCodeSender")
public class EmailCodeSender implements ICodeSender {
    private JavaMailSender mailSender;
    @Async
    @Override
    public void send(String target, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(target);
        message.setSubject("注册验证码");
        message.setText("您的验证码是：" + code);
        mailSender.send(message);
    }
}
