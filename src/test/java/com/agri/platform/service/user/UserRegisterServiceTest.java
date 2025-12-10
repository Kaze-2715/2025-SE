package com.agri.platform.service.user;

import com.agri.platform.DTO.UserRegisterDTO;
import com.agri.platform.entity.user.User;
import com.agri.platform.enums.UserLoginType;
import com.agri.platform.exception.BizException;
import com.agri.platform.mapper.user.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserRegisterServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private VerifyCodeService verifyCodeService;

    @InjectMocks
    private UserRegisterService userRegisterService;

    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        encoder = new BCryptPasswordEncoder();
    }

    @Test
    void testRegisterUser_Success_Username() {
        // 准备测试数据
        UserRegisterDTO dto = new UserRegisterDTO(
                UserLoginType.USERNAME,
                "testuser",
                "Test@123456",
                null);

        // 模拟依赖行为
        when(userMapper.countByUsername("testuser")).thenReturn(0);
        when(userMapper.countByEmail(anyString())).thenReturn(0);
        when(userMapper.countByPhoneNumber(anyString())).thenReturn(0);

        // 执行测试方法
        User result = userRegisterService.registerUser(dto);

        // 验证结果
        assertNotNull(result);
        assertNotNull(result.getUserId());
        assertEquals("testuser", result.getUsername());
        assertNull(result.getEmail());
        assertNull(result.getPhoneNumber());
        assertEquals(User.AccountStatus.PENDING, result.getAccountStatus());
        assertNotNull(result.getRegistrationTime());
        assertEquals(0, result.getLoginFailCount());
        assertNull(result.getLoginLockedUntil());

        // 验证依赖调用
        verify(userMapper).countByUsername("testuser");
        verify(userMapper).insertUser(any(User.class));
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userMapper).insertUser(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        assertTrue(encoder.matches("Test@123456", capturedUser.getPasswordHash()));
    }

    @Test
    void testRegisterUser_Success_Email() {
        // 准备测试数据
        UserRegisterDTO dto = new UserRegisterDTO(
                UserLoginType.EMAIL,
                "test@example.com",
                "Test@123456",
                "123456");

        // 模拟依赖行为
        when(userMapper.countByUsername(anyString())).thenReturn(0);
        when(userMapper.countByEmail("test@example.com")).thenReturn(0);
        when(userMapper.countByPhoneNumber(anyString())).thenReturn(0);
        when(verifyCodeService.verify("test@example.com", "REGISTER", "123456")).thenReturn(true);

        // 执行测试方法
        User result = userRegisterService.registerUser(dto);

        // 验证结果
        assertNotNull(result);
        assertNotNull(result.getUserId());
        assertNull(result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertNull(result.getPhoneNumber());
        assertEquals(User.AccountStatus.PENDING, result.getAccountStatus());

        // 验证依赖调用
        verify(userMapper).countByEmail("test@example.com");
        verify(verifyCodeService).verify("test@example.com", "REGISTER", "123456");
        verify(userMapper).insertUser(any(User.class));
    }

    @Test
    void testRegisterUser_BlankLogin_ShouldThrowException() {
        // 准备测试数据
        UserRegisterDTO dto = new UserRegisterDTO(
                UserLoginType.USERNAME,
                "",
                "Test@123456",
                null);

        // 执行测试并验证异常
        BizException exception = assertThrows(BizException.class, () -> {
            userRegisterService.registerUser(dto);
        });

        assertEquals("用户名、邮箱或手机号至少填一项！", exception.getMessage());
        verify(userMapper, never()).countByUsername(anyString());
    }

    @Test
    void testRegisterUser_NullPassword_ShouldThrowException() {
        // 准备测试数据
        UserRegisterDTO dto = new UserRegisterDTO(
                UserLoginType.USERNAME,
                "testuser",
                null,
                null);

        // 执行测试并验证异常
        BizException exception = assertThrows(BizException.class, () -> {
            userRegisterService.registerUser(dto);
        });

        assertEquals("密码不能为空！", exception.getMessage());
        verify(userMapper, never()).countByUsername(anyString());
    }

    @Test
    void testRegisterUser_ExistingUsername_ShouldThrowException() {
        // 准备测试数据
        UserRegisterDTO dto = new UserRegisterDTO(
                UserLoginType.USERNAME,
                "existinguser",
                "Test@123456",
                null);

        // 模拟依赖行为
        when(userMapper.countByUsername("existinguser")).thenReturn(1);

        // 执行测试并验证异常
        BizException exception = assertThrows(BizException.class, () -> {
            userRegisterService.registerUser(dto);
        });

        assertEquals("用户已存在！", exception.getMessage());
        verify(userMapper).countByUsername("existinguser");
        verify(userMapper, never()).insertUser(any(User.class));
    }

    @Test
    void testRegisterUser_ExistingEmail_ShouldThrowException() {
        // 准备测试数据
        UserRegisterDTO dto = new UserRegisterDTO(
                UserLoginType.EMAIL,
                "existing@example.com",
                "Test@123456",
                "123456");

        // 模拟依赖行为
        when(userMapper.countByUsername(anyString())).thenReturn(0);
        when(userMapper.countByEmail("existing@example.com")).thenReturn(1);

        // 执行测试并验证异常
        BizException exception = assertThrows(BizException.class, () -> {
            userRegisterService.registerUser(dto);
        });

        assertEquals("用户已存在！", exception.getMessage());
        verify(userMapper).countByEmail("existing@example.com");
        verify(userMapper, never()).insertUser(any(User.class));
    }

    @Test
    void testRegisterUser_InvalidVerifyCode_ShouldThrowException() {
        // 准备测试数据
        UserRegisterDTO dto = new UserRegisterDTO(
                UserLoginType.EMAIL,
                "test@example.com",
                "Test@123456",
                "123456");

        // 模拟依赖行为
        when(userMapper.countByUsername(anyString())).thenReturn(0);
        when(userMapper.countByEmail("test@example.com")).thenReturn(0);
        when(userMapper.countByPhoneNumber(anyString())).thenReturn(0);
        when(verifyCodeService.verify("test@example.com", "REGISTER", "123456")).thenReturn(false);

        // 执行测试并验证异常
        BizException exception = assertThrows(BizException.class, () -> {
            userRegisterService.registerUser(dto);
        });

        assertEquals("无效的验证码！", exception.getMessage());
        verify(verifyCodeService).verify("test@example.com", "REGISTER", "123456");
        verify(userMapper, never()).insertUser(any(User.class));
    }
}