package com.agri.platform.controller.user;

import com.agri.platform.DTO.UserUpdateDTO;
import com.agri.platform.entity.user.User;
import com.agri.platform.mapper.user.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@Transactional
public class UserUpdateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserMapper userMapper;

    @Test
    void testUpdateUser() throws Exception {
        // 使用测试数据中的用户ID
        String userId = "u123";

        // 创建更新DTO
        UserUpdateDTO updateDTO = new UserUpdateDTO(
                userId,
                "UpdatedUsername", // 更新用户名
                null,
                null);

        // 发送更新请求
        mockMvc.perform(put("/api/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO))
                .with(csrf()))
                .andExpect(status().isNoContent());

        // 验证数据库中的用户是否已更新
        Optional<User> updatedUser = userMapper.selectById(userId);
        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getUsername()).isEqualTo("UpdatedUsername");
    }
}