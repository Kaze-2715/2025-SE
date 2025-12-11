package com.agri.platform.userTests;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.agri.platform.DTO.UserUpdateDTO;
import com.agri.platform.entity.user.User;
import com.agri.platform.mapper.user.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser
public class TestUserUpdate {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserMapper userMapper;

    private final String TEST_UID = "974a8920-e829-48df-b058-e6c724e1bfa0";

    @Test
    void should_return_204_when_update_user_success() throws Exception {
        UserUpdateDTO dto = new UserUpdateDTO(
                TEST_UID,
                "updated_name", // 只改用户名
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        // 发请求
        mockMvc.perform(
                put("/api/user/update")
               .contentType(MediaType.APPLICATION_JSON)
               .content(new ObjectMapper().writeValueAsString(dto)).with(csrf())
        )
        .andExpect(status().isNoContent()); // 204

        // 直接查库验证
        User user = userMapper.selectById(TEST_UID).get();
        assertThat(user.getUsername()).isEqualTo("updated_name");
    }
}
