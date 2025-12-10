package com.agri.platform.controller.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.agri.platform.DTO.UserLoginDTO;
import com.agri.platform.entity.user.User;
import com.agri.platform.enums.UserLoginType;
import com.agri.platform.exception.BizException;
import com.agri.platform.service.user.UserLoginService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserLoginService loginService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testLogin() throws Exception {
        // 准备测试数据
        UserLoginDTO loginDTO = new UserLoginDTO("testUser", "password123", UserLoginType.USERNAME, "127.0.0.1");
        // ! 注意：这里的User对象需要根据实际情况进行创建
        User user = new User();
        user.setUserId("testUserId");
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setPhoneNumber("13800138000");

        // 模拟服务层行为
        when(loginService.loginUser(any(UserLoginDTO.class), any())).thenReturn(user);

        // 执行测试
        mockMvc.perform(post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getUserId()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(user.getPhoneNumber()))
                .andExpect(jsonPath("$.message").value("登录成功"));

        // 验证服务层调用
        verify(loginService, times(1)).loginUser(any(UserLoginDTO.class), any());
    }

    @Test
    void testLogin_BizException() throws Exception {
        // 准备测试数据
        UserLoginDTO loginDTO = new UserLoginDTO("testUser", "password123", UserLoginType.USERNAME, "127.0.0.1");
        BizException exception = new BizException("登录失败");

        // 模拟服务层抛出业务异常
        when(loginService.loginUser(any(UserLoginDTO.class), any())).thenThrow(exception);

        // 执行测试
        mockMvc.perform(post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("Error"))
                .andExpect(jsonPath("$.login").value(loginDTO.login()))
                .andExpect(jsonPath("$.message").value("登录失败"));

        // 验证服务层调用
        verify(loginService, times(1)).loginUser(any(UserLoginDTO.class), any());
    }

    @Test
    void testLogin_SystemException() throws Exception {
        // 准备测试数据
        UserLoginDTO loginDTO = new UserLoginDTO("testUser", "password123", UserLoginType.USERNAME, "127.0.0.1");

        // 模拟服务层抛出系统异常
        when(loginService.loginUser(any(UserLoginDTO.class), any())).thenThrow(new RuntimeException("系统异常"));

        // 执行测试
        mockMvc.perform(post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("Error"))
                .andExpect(jsonPath("$.login").value(loginDTO.login()))
                .andExpect(jsonPath("$.message").value("系统异常，请稍后再试"));

        // 验证服务层调用
        verify(loginService, times(1)).loginUser(any(UserLoginDTO.class), any());
    }
}