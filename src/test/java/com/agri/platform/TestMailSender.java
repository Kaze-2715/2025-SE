package com.agri.platform;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.agri.platform.util.EmailCodeSender;

@ExtendWith(MockitoExtension.class)
public class TestMailSender {
    @Mock
    private JavaMailSender mailSender;
    @InjectMocks
    private EmailCodeSender emailCodeSender;

    @Test
    void sendActiveEmail_shouldFillCorrectly() throws Exception {
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        emailCodeSender.send("1983517529@qq.com", "123456");
        verify(mailSender).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertEquals("1983517529@qq.com", sentMessage.getTo()[0]);
    }
    
}
