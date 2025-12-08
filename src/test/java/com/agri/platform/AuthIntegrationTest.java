package com.agri.platform;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser
public class AuthIntegrationTest {
    @Autowired
    private MockMvc mvc;

    /* 工具：模拟登录，返回带权限的 Session */
    private MockHttpSession login() throws Exception {
        MockHttpSession session = (MockHttpSession) mvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"Test@123456\",\"password\":\"Test@123456\",\"type\":\"USERNAME\",\"ip\":\"127.0.0.1\"}").with(csrf()))
                .andExpect(status().isOk())
                .andReturn()
                .getRequest()
                .getSession();
        return session;
    }

    @Test
    void whenHasPerm_then200() throws Exception {
        MockHttpSession session = login();
        mvc.perform(get("/api/farm").session(session))
                .andExpect(status().isOk());
    }

    @Test
    void whenNoPerm_then403() throws Exception {
        MockHttpSession session = login();
        // 假设 /api/device/control 需要 "设备-控制" 权限，而用户没有
        mvc.perform(post("/api/device/control").session(session))
                .andExpect(status().isForbidden());
    }
}
