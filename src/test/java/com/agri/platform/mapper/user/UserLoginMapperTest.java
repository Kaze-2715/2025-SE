package com.agri.platform.mapper.user;

import com.agri.platform.entity.user.LoginLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserLoginMapperTest {

    @Autowired
    private UserLoginMapper userLoginMapper;

    @Test
    void testCount() {
        // Given: 插入一些测试数据
        LoginLog log1 = new LoginLog();
        log1.setUserId("user1");
        log1.setLoginTime(LocalDateTime.now());
        log1.setLoginIp("192.168.1.1");
        log1.setUserAgent("Mozilla/5.0");
        log1.setSuccess(true);
        log1.setFailReason(null);

        LoginLog log2 = new LoginLog();
        log2.setUserId("user2");
        log2.setLoginTime(LocalDateTime.now());
        log2.setLoginIp("192.168.1.2");
        log2.setUserAgent("Mozilla/5.0");
        log2.setSuccess(false);
        log2.setFailReason("Invalid credentials");

        userLoginMapper.insertLog(log1);
        userLoginMapper.insertLog(log2);

        // When: 调用count方法
        long count = userLoginMapper.count();

        // Then: 验证结果
        assertThat(count).isEqualTo(2L);
    }

    @Test
    void testDeleteAll() {
        // Given: 插入一些测试数据
        LoginLog log = new LoginLog();
        log.setUserId("user1");
        log.setLoginTime(LocalDateTime.now());
        log.setLoginIp("192.168.1.1");
        log.setUserAgent("Mozilla/5.0");
        log.setSuccess(true);
        log.setFailReason(null);

        userLoginMapper.insertLog(log);

        // 确认数据已插入
        assertThat(userLoginMapper.count()).isEqualTo(1L);

        // When: 调用deleteAll方法
        int deletedRows = userLoginMapper.deleteAll();

        // Then: 验证结果
        assertThat(deletedRows).isEqualTo(1);
        assertThat(userLoginMapper.count()).isEqualTo(0L);
    }

    @Test
    void testInsertLog() {
        // Given: 创建一个LoginLog对象
        LoginLog log = new LoginLog();
        log.setUserId("testUser");
        log.setLoginTime(LocalDateTime.of(2023, 1, 1, 12, 0, 0));
        log.setLoginIp("192.168.1.100");
        log.setUserAgent("Test Agent");
        log.setSuccess(true);
        log.setFailReason(null);

        // When: 调用insertLog方法
        int insertedRows = userLoginMapper.insertLog(log);

        // Then: 验证结果
        assertThat(insertedRows).isEqualTo(1);
        assertThat(userLoginMapper.count()).isEqualTo(1L);

        // 验证插入的数据是否正确
        List<LoginLog> logs = userLoginMapper.selectAll();
        assertThat(logs).hasSize(1);
        LoginLog insertedLog = logs.get(0);
        assertThat(insertedLog.getUserId()).isEqualTo("testUser");
        assertThat(insertedLog.getLoginIp()).isEqualTo("192.168.1.100");
        assertThat(insertedLog.getUserAgent()).isEqualTo("Test Agent");
        assertThat(insertedLog.isSuccess()).isTrue();
        assertThat(insertedLog.getFailReason()).isNull();
    }

    @Test
    void testSelectAll() {
        // Given: 插入一些测试数据
        LoginLog log1 = new LoginLog();
        log1.setUserId("user1");
        log1.setLoginTime(LocalDateTime.of(2023, 1, 1, 12, 0, 0));
        log1.setLoginIp("192.168.1.1");
        log1.setUserAgent("Agent 1");
        log1.setSuccess(true);
        log1.setFailReason(null);

        LoginLog log2 = new LoginLog();
        log2.setUserId("user2");
        log2.setLoginTime(LocalDateTime.of(2023, 1, 2, 12, 0, 0));
        log2.setLoginIp("192.168.1.2");
        log2.setUserAgent("Agent 2");
        log2.setSuccess(false);
        log2.setFailReason("Authentication failed");

        userLoginMapper.insertLog(log1);
        userLoginMapper.insertLog(log2);

        // When: 调用selectAll方法
        List<LoginLog> logs = userLoginMapper.selectAll();

        // Then: 验证结果
        assertThat(logs).hasSize(2);

        // 验证第一条记录
        LoginLog selectedLog1 = logs.stream()
                .filter(l -> "user1".equals(l.getUserId()))
                .findFirst()
                .orElse(null);
        assertThat(selectedLog1).isNotNull();
        assertThat(selectedLog1.getLoginIp()).isEqualTo("192.168.1.1");
        assertThat(selectedLog1.getUserAgent()).isEqualTo("Agent 1");
        assertThat(selectedLog1.isSuccess()).isTrue();
        assertThat(selectedLog1.getFailReason()).isNull();

        // 验证第二条记录
        LoginLog selectedLog2 = logs.stream()
                .filter(l -> "user2".equals(l.getUserId()))
                .findFirst()
                .orElse(null);
        assertThat(selectedLog2).isNotNull();
        assertThat(selectedLog2.getLoginIp()).isEqualTo("192.168.1.2");
        assertThat(selectedLog2.getUserAgent()).isEqualTo("Agent 2");
        assertThat(selectedLog2.isSuccess()).isFalse();
        assertThat(selectedLog2.getFailReason()).isEqualTo("Authentication failed");
    }
}