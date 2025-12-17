package com.agri.platform.service.user;

import com.agri.platform.DTO.UserLoginDTO;
import com.agri.platform.entity.user.User;
import com.agri.platform.enums.UserLoginType;
import com.agri.platform.exception.BizException;
import com.agri.platform.mapper.user.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserLoginServiceTest {

    @InjectMocks
    private UserLoginService userLoginService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder encoder;

    private MockHttpServletRequest request;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        request.setSession(session);
    }

    @Test
    void testLoginUser_Success() {
        // Given
        String login = "test@example.com";
        String password = "Test@123456";
        String hashedPassword = "$2a$10$/bpb0Okb7Kize3EG7KYvrespW0BK1wVqP8yFzIBMJl4vwmFcAwOXO";
        UserLoginDTO dto = new UserLoginDTO(login, password, UserLoginType.EMAIL, "127.0.0.1");

        User user = new User();
        user.setUserId("u123");
        user.setEmail(login);
        user.setPasswordHash(hashedPassword);
        user.setAccountStatus(User.AccountStatus.ACTIVE);
        user.setLoginFailCount(0);

        when(userMapper.selectByEmail(login)).thenReturn(Optional.of(user));
        when(encoder.matches(password, hashedPassword)).thenReturn(true);

        // When
        User result = userLoginService.loginUser(dto, request);

        // Then
        assertNotNull(result);
        assertEquals("u123", result.getUserId());
        assertEquals(login, result.getEmail());

        // Verify session attributes
        HttpSession session = request.getSession();
        assertEquals("u123", session.getAttribute("userId"));
        assertNotNull(session.getAttribute("perms"));

        // Verify user update
        verify(userMapper).updateLoginInfo(any(User.class));
    }

    @Test
    void testLoginUser_WithEmptyLogin_ShouldThrowException() {
        // Given
        UserLoginDTO dto = new UserLoginDTO("", "password", UserLoginType.EMAIL, "127.0.0.1");

        // When & Then
        BizException exception = assertThrows(BizException.class, () -> {
            userLoginService.loginUser(dto, request);
        });

        assertEquals("用户名、邮箱或手机号不能为空！", exception.getMessage());
    }

    @Test
    void testLoginUser_WithNullLogin_ShouldThrowException() {
        // Given
        UserLoginDTO dto = new UserLoginDTO(null, "password", UserLoginType.EMAIL, "127.0.0.1");

        // When & Then
        BizException exception = assertThrows(BizException.class, () -> {
            userLoginService.loginUser(dto, request);
        });

        assertEquals("用户名、邮箱或手机号不能为空！", exception.getMessage());
    }

    @Test
    void testLoginUser_WithEmptyPassword_ShouldThrowException() {
        // Given
        UserLoginDTO dto = new UserLoginDTO("test@example.com", "", UserLoginType.EMAIL, "127.0.0.1");

        // When & Then
        BizException exception = assertThrows(BizException.class, () -> {
            userLoginService.loginUser(dto, request);
        });

        assertEquals("密码不能为空！", exception.getMessage());
    }

    @Test
    void testLoginUser_WithNullPassword_ShouldThrowException() {
        // Given
        UserLoginDTO dto = new UserLoginDTO("test@example.com", null, UserLoginType.EMAIL, "127.0.0.1");

        // When & Then
        BizException exception = assertThrows(BizException.class, () -> {
            userLoginService.loginUser(dto, request);
        });

        assertEquals("密码不能为空！", exception.getMessage());
    }

    @Test
    void testLoginUser_UserNotFound_ShouldThrowException() {
        // Given
        UserLoginDTO dto = new UserLoginDTO("nonexistent@example.com", "password", UserLoginType.EMAIL, "127.0.0.1");

        when(userMapper.selectByEmail(dto.login())).thenReturn(Optional.empty());

        // When & Then
        BizException exception = assertThrows(BizException.class, () -> {
            userLoginService.loginUser(dto, request);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("用户不存在！", exception.getMessage());
    }

    @Test
    void testLoginUser_FrozenAccount_ShouldThrowException() {
        // Given
        String login = "frozen@example.com";
        String password = "password";
        UserLoginDTO dto = new UserLoginDTO(login, password, UserLoginType.EMAIL, "127.0.0.1");

        User user = new User();
        user.setAccountStatus(User.AccountStatus.FROZEN);

        when(userMapper.selectByEmail(login)).thenReturn(Optional.of(user));

        // When & Then
        BizException exception = assertThrows(BizException.class, () -> {
            userLoginService.loginUser(dto, request);
        });

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
        assertEquals("账户已冻结！", exception.getMessage());
    }

    @Test
    void testLoginUser_WrongPassword_ShouldThrowException() {
        // Given
        String login = "test@example.com";
        String password = "wrongpassword";
        String hashedPassword = "$2a$10$/bpb0Okb7Kize3EG7KYvrespW0BK1wVqP8yFzIBMJl4vwmFcAwOXO";
        UserLoginDTO dto = new UserLoginDTO(login, password, UserLoginType.EMAIL, "127.0.0.1");

        User user = new User();
        user.setUserId("u123");
        user.setEmail(login);
        user.setPasswordHash(hashedPassword);
        user.setAccountStatus(User.AccountStatus.ACTIVE);
        user.setLoginFailCount(0);

        when(userMapper.selectByEmail(login)).thenReturn(Optional.of(user));
        when(encoder.matches(password, hashedPassword)).thenReturn(false);

        // When & Then
        BizException exception = assertThrows(BizException.class, () -> {
            userLoginService.loginUser(dto, request);
        });

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        assertEquals("密码错误！", exception.getMessage());

        // Verify login fail handling
        verify(userMapper).updateLoginInfo(any(User.class));
    }

    @Test
    void testLoginUser_UsernameType_Success() {
        // Given
        String login = "testuser";
        String password = "Test@123456";
        String hashedPassword = "$2a$10$/bpb0Okb7Kize3EG7KYvrespW0BK1wVqP8yFzIBMJl4vwmFcAwOXO";
        UserLoginDTO dto = new UserLoginDTO(login, password, UserLoginType.USERNAME, "127.0.0.1");

        User user = new User();
        user.setUserId("u123");
        user.setUsername(login);
        user.setPasswordHash(hashedPassword);
        user.setAccountStatus(User.AccountStatus.ACTIVE);
        user.setLoginFailCount(0);

        when(userMapper.selectByUsername(login)).thenReturn(Optional.of(user));
        when(encoder.matches(password, hashedPassword)).thenReturn(true);

        // When
        User result = userLoginService.loginUser(dto, request);

        // Then
        assertNotNull(result);
        assertEquals("u123", result.getUserId());
        assertEquals(login, result.getUsername());
    }

    @Test
    void testLoginUser_PhoneNumberType_Success() {
        // Given
        String login = "13800138000";
        String password = "Test@123456";
        String hashedPassword = "$2a$10$/bpb0Okb7Kize3EG7KYvrespW0BK1wVqP8yFzIBMJl4vwmFcAwOXO";
        UserLoginDTO dto = new UserLoginDTO(login, password, UserLoginType.PHONE_NUMBER, "127.0.0.1");

        User user = new User();
        user.setUserId("u123");
        user.setPhoneNumber(login);
        user.setPasswordHash(hashedPassword);
        user.setAccountStatus(User.AccountStatus.ACTIVE);
        user.setLoginFailCount(0);

        when(userMapper.selectByPhoneNumber(login)).thenReturn(Optional.of(user));
        when(encoder.matches(password, hashedPassword)).thenReturn(true);

        // When
        User result = userLoginService.loginUser(dto, request);

        // Then
        assertNotNull(result);
        assertEquals("u123", result.getUserId());
        assertEquals(login, result.getPhoneNumber());
    }

    @Test
    void testLoginUser_AccountLockedAfterThreeFailures() {
        // Given
        String login = "test@example.com";
        String password = "wrongpassword";
        String hashedPassword = "$2a$10$/bpb0Okb7Kize3EG7KYvrespW0BK1wVqP8yFzIBMJl4vwmFcAwOXO";
        UserLoginDTO dto = new UserLoginDTO(login, password, UserLoginType.EMAIL, "127.0.0.1");

        User user = new User();
        user.setUserId("u123");
        user.setEmail(login);
        user.setPasswordHash(hashedPassword);
        user.setAccountStatus(User.AccountStatus.ACTIVE);
        user.setLoginFailCount(2); // Already had 2 failures

        when(userMapper.selectByEmail(login)).thenReturn(Optional.of(user));
        when(encoder.matches(password, hashedPassword)).thenReturn(false);

        // When & Then
        BizException exception = assertThrows(BizException.class, () -> {
            userLoginService.loginUser(dto, request);
        });

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        assertEquals("密码错误！", exception.getMessage());

        // Verify that loginLockedUntil is set
        verify(userMapper).updateLoginInfo(argThat(u -> u.getLoginLockedUntil() != null));
    }
}