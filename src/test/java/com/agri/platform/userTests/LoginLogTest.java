package com.agri.platform.userTests;

import com.agri.platform.entity.user.LoginLog;
import com.agri.platform.mapper.user.UserLoginMapper;
import org.springframework.security.test.context.support.WithMockUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@WithMockUser
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginLogTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserLoginMapper loginLogMapper;

    @AfterAll
    static void tearDown(@Autowired UserLoginMapper loginLogMapper) {
        loginLogMapper.deleteAll(); // 用 MyBatis 清
    }

    @Test
    @Order(1)
    void success_login_should_write_log() throws Exception {
        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"login":"1983517529@qq.com","password":"Test@123456", "type":"EMAIL", "ip":"127.0.0.1"}
                                """).with(csrf()).with(req -> {
                    req.setRemoteAddr("127.0.0.1"); // 设置 remoteAddr
                    req.addHeader("X-Forwarded-For", "192.168.1.100, 10.0.0.1");
                    return req;
                }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("登录成功"));

        Thread.sleep(1_000);
        System.out.println("测试里 count = " + loginLogMapper.count());
        List<LoginLog> logs = loginLogMapper.selectAll();
        System.out.println("库里的 userId = " + logs.get(0).getUserId());
        assertThat(logs).hasSize(1);
        assertThat(logs.get(0).getUserId()).isEqualTo("974a8920-e829-48df-b058-e6c724e1bfa0");
        assertThat(logs.get(0).isSuccess()).isTrue();
        assertThat(logs.get(0).getFailReason()).isNull();
    }

    @Test
    @Order(2)
    void fail_login_should_write_log_with_reason() throws Exception {
        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"login":"1983517529@qq.com","password":"wrongpassword", "type":"EMAIL", "ip":"127.0.0.1"}
                                """).with(csrf()).with(req -> {
                    req.setRemoteAddr("127.0.0.1"); // 设置 remoteAddr
                    req.addHeader("X-Forwarded-For", "192.168.1.100, 10.0.0.1");
                    return req;
                }))
                .andExpect(status().isUnauthorized());

        Thread.sleep(1_000);
        List<LoginLog> logs = loginLogMapper.selectAll();
        assertThat(logs).hasSize(1);
        LoginLog failLog = logs.get(0);
        assertThat(failLog.isSuccess()).isFalse();
        // assertThat(failLog.getFailReason()).contains("密码错误");
    }
}
