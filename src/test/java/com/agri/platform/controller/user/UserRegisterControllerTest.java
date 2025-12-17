package com.agri.platform.controller.user;

import com.agri.platform.DTO.UserRegisterDTO;
import com.agri.platform.entity.user.VerifyCode;
import com.agri.platform.enums.UserLoginType;
import com.agri.platform.mapper.user.VerifyCodeMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@Transactional
public class UserRegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VerifyCodeMapper verifyCodeMapper;

    @Test
    void testRegister() throws Exception {
        String email = "test@example.com";

        // 1. 发送验证码
        mockMvc.perform(post("/api/verify-code/send")
                .param("target", email)
                .param("type", "EMAIL")
                .with(csrf()))
                .andExpect(status().isOk());

        // 2. 从数据库获取最新验证码
        VerifyCode vc = verifyCodeMapper.findLatestCode("REGISTER", email);
        String correctCode = vc.getCode();

        // 3. 使用正确的验证码注册用户
        UserRegisterDTO registerDTO = new UserRegisterDTO(
                UserLoginType.EMAIL,
                email,
                "Test@123456",
                correctCode);

        mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    void testRegisterWithoutPassword() throws Exception {
        String email = "test2@example.com";

        // 1. 发送验证码
        mockMvc.perform(post("/api/verify-code/send")
                .param("target", email)
                .param("type", "EMAIL")
                .with(csrf()))
                .andExpect(status().isOk());

        // 2. 从数据库获取最新验证码
        VerifyCode vc = verifyCodeMapper.findLatestCode("REGISTER", email);
        String correctCode = vc.getCode();

        // 3. 使用空密码尝试注册
        UserRegisterDTO registerDTO = new UserRegisterDTO(
                UserLoginType.EMAIL,
                email,
                "", // 空密码
                correctCode);

        mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO))
                .with(csrf()))
                .andExpect(status().isBadRequest());
    }
}