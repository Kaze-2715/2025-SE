package com.agri.platform.controller.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@Transactional
public class VerifyCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testSendRegisterCode() throws Exception {
        // 测试发送注册验证码成功的情况
        mockMvc.perform(post("/api/verify-code/send")
                .param("target", "test@example.com")
                .param("type", "EMAIL")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("验证码发送成功"));
    }

    @Test
    void testSendRegisterCodeWithInvalidType() throws Exception {
        // 测试使用不支持的类型发送验证码失败的情况
        mockMvc.perform(post("/api/verify-code/send")
                .param("target", "13800138000")
                .param("type", "PHONE_NUMBER")
                .with(csrf()))
                .andExpect(status().isBadRequest());
    }
}