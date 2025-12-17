package com.agri.platform.service.user;

import com.agri.platform.entity.user.LoginLog;
import com.agri.platform.mapper.user.UserLoginMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class LoginLogServiceTest {

    private LoginLogService loginLogService;

    @Mock
    private UserLoginMapper userLoginMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginLogService = new LoginLogService(userLoginMapper);
    }

    @Test
    void testSave_SuccessfulLogin() {
        // 准备测试数据
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId("test-user-id");
        loginLog.setLoginTime(LocalDateTime.of(2023, 1, 1, 12, 0, 0));
        loginLog.setLoginIp("192.168.1.1");
        loginLog.setUserAgent("Mozilla/5.0");
        loginLog.setSuccess(true);
        loginLog.setFailReason(null);

        // 调用被测试方法
        loginLogService.save(loginLog);

        // 验证结果
        ArgumentCaptor<LoginLog> captor = ArgumentCaptor.forClass(LoginLog.class);
        verify(userLoginMapper, times(1)).insertLog(captor.capture());

        LoginLog capturedLog = captor.getValue();
        assertThat(capturedLog.getUserId()).isEqualTo("test-user-id");
        assertThat(capturedLog.getLoginTime()).isEqualTo(LocalDateTime.of(2023, 1, 1, 12, 0, 0));
        assertThat(capturedLog.getLoginIp()).isEqualTo("192.168.1.1");
        assertThat(capturedLog.getUserAgent()).isEqualTo("Mozilla/5.0");
        assertThat(capturedLog.isSuccess()).isTrue();
        assertThat(capturedLog.getFailReason()).isNull();
    }

    @Test
    void testSave_FailedLogin() {
        // 准备测试数据
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId("test-user-id");
        loginLog.setLoginTime(LocalDateTime.of(2023, 1, 1, 12, 0, 0));
        loginLog.setLoginIp("192.168.1.1");
        loginLog.setUserAgent("Mozilla/5.0");
        loginLog.setSuccess(false);
        loginLog.setFailReason("密码错误");

        // 调用被测试方法
        loginLogService.save(loginLog);

        // 验证结果
        ArgumentCaptor<LoginLog> captor = ArgumentCaptor.forClass(LoginLog.class);
        verify(userLoginMapper, times(1)).insertLog(captor.capture());

        LoginLog capturedLog = captor.getValue();
        assertThat(capturedLog.getUserId()).isEqualTo("test-user-id");
        assertThat(capturedLog.getLoginTime()).isEqualTo(LocalDateTime.of(2023, 1, 1, 12, 0, 0));
        assertThat(capturedLog.getLoginIp()).isEqualTo("192.168.1.1");
        assertThat(capturedLog.getUserAgent()).isEqualTo("Mozilla/5.0");
        assertThat(capturedLog.isSuccess()).isFalse();
        assertThat(capturedLog.getFailReason()).isEqualTo("密码错误");
    }

    @Test
    void testSave_EmptyFields() {
        // 准备测试数据
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId("");
        loginLog.setLoginTime(LocalDateTime.MIN);
        loginLog.setLoginIp("");
        loginLog.setUserAgent("");
        loginLog.setSuccess(false);
        loginLog.setFailReason("");

        // 调用被测试方法
        loginLogService.save(loginLog);

        // 验证结果
        ArgumentCaptor<LoginLog> captor = ArgumentCaptor.forClass(LoginLog.class);
        verify(userLoginMapper, times(1)).insertLog(captor.capture());

        LoginLog capturedLog = captor.getValue();
        assertThat(capturedLog.getUserId()).isEqualTo("");
        assertThat(capturedLog.getLoginTime()).isEqualTo(LocalDateTime.MIN);
        assertThat(capturedLog.getLoginIp()).isEqualTo("");
        assertThat(capturedLog.getUserAgent()).isEqualTo("");
        assertThat(capturedLog.isSuccess()).isFalse();
        assertThat(capturedLog.getFailReason()).isEqualTo("");
    }

    @Test
    void testSave_NullFields() {
        // 准备测试数据
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(null);
        loginLog.setLoginTime(null);
        loginLog.setLoginIp(null);
        loginLog.setUserAgent(null);
        loginLog.setSuccess(false);
        loginLog.setFailReason(null);

        // 调用被测试方法
        loginLogService.save(loginLog);

        // 验证结果
        ArgumentCaptor<LoginLog> captor = ArgumentCaptor.forClass(LoginLog.class);
        verify(userLoginMapper, times(1)).insertLog(captor.capture());

        LoginLog capturedLog = captor.getValue();
        assertThat(capturedLog.getUserId()).isNull();
        assertThat(capturedLog.getLoginTime()).isNull();
        assertThat(capturedLog.getLoginIp()).isNull();
        assertThat(capturedLog.getUserAgent()).isNull();
        assertThat(capturedLog.isSuccess()).isFalse();
        assertThat(capturedLog.getFailReason()).isNull();
    }

    @Test
    void testSave_VerifyMapperCalled() {
        // 准备测试数据
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId("test-user-id");
        loginLog.setLoginTime(LocalDateTime.now());
        loginLog.setLoginIp("127.0.0.1");
        loginLog.setUserAgent("Test Agent");
        loginLog.setSuccess(true);
        loginLog.setFailReason(null);

        // 调用被测试方法
        loginLogService.save(loginLog);

        // 验证mapper方法被调用了一次
        verify(userLoginMapper, times(1)).insertLog(any(LoginLog.class));
    }

    @Test
    void testSave_VerifyNewInstanceCreated() {
        // 准备测试数据
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId("test-user-id");

        // 使用spy来验证copyProperties被调用
        LoginLog spyLoginLog = spy(loginLog);

        // 创建一个新的service实例，使用spy对象
        LoginLogService service = new LoginLogService(userLoginMapper);

        // 调用被测试方法
        service.save(spyLoginLog);

        // 验证mapper方法被调用
        verify(userLoginMapper, times(1)).insertLog(any(LoginLog.class));
    }
}