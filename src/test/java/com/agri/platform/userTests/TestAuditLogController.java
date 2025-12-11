package com.agri.platform.userTests;

import com.agri.platform.entity.log.AuditLog;
import com.agri.platform.mapper.log.AuditLogMapper;

import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.core.env.Environment;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser
@ActiveProfiles("test")
public class TestAuditLogController {

    @Autowired
    private AuditLogMapper auditLogMapper;

    @Autowired
    private DummyBean dummyBean;

    @Autowired
    private Environment env;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void printProp() {
        System.out.println("platform.audit.enable = " + env.getProperty("platform.audit.enable"));
    }

    @Test
    void auditLogShouldBeInserted() throws Exception {
        MvcResult result = mockMvc.perform(
                post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {"login":"1983517529@qq.com","password":"Test@123456", "type":"EMAIL", "ip":"127.0.0.1"}
                                        """)
                        .with(csrf()).with(req -> {
                            req.setRemoteAddr("127.0.0.1"); // 设置 remoteAddr
                            req.addHeader("X-Forwarded-For", "192.168.1.100, 10.0.0.1");
                            return req;
                        }))
                .andExpect(status().isOk()).andReturn();
        /* 2. 取出登录时创建的 Session 并绑定到当前线程 */
        HttpSession session = result.getRequest().getSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Long bizTargetId = dummyBean.doSomething("10086");

        /* 2. 立即查库（同一事务） */
        TestTransaction.flagForCommit();
        TestTransaction.end();
        List<AuditLog> logs = auditLogMapper.getAuditLogsByTarget("DUMMY", Long.valueOf(bizTargetId));
        assertFalse(logs.isEmpty());
        AuditLog log = logs.get(0);
        System.out.println("log = " + log);
        /* 3. 断言 */
        assertNotNull(log);
        assertEquals("DUMMY", log.getTargetType());
        assertNotNull(log.getIp());
        assertNotNull(log.getOperateTime());
    }
}