package com.agri.platform;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import com.agri.platform.DTO.UserRegisterDTO;
import com.agri.platform.entity.user.User;
import com.agri.platform.enums.UserLoginType;
import com.agri.platform.exception.BizException;
import com.agri.platform.mapper.user.UserMapper;
import com.agri.platform.service.user.UserRegisterService;

@ExtendWith(MockitoExtension.class)
public class RegisterTests {
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserRegisterService userRegisterService;

    @Test
    void testNormalRegister() {
        UserRegisterDTO byUsername = new UserRegisterDTO(UserLoginType.USERNAME, "testuser", "password123");

        UserRegisterDTO byEmail = new UserRegisterDTO(UserLoginType.EMAIL, "1983517529@qq.com", "password123");

        UserRegisterDTO byPhoneNumber = new UserRegisterDTO(UserLoginType.PHONE_NUMBER, "13517529835", "password123");

        assertDoesNotThrow(() -> userRegisterService.registerUser(byUsername));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userMapper, times(1)).insertUser(captor.capture());

        User capturedUser = captor.getValue();

        assertEquals("testuser", capturedUser.getUsername());
        assertDoesNotThrow(() -> userRegisterService.registerUser(byEmail));

        verify(userMapper, times(2)).insertUser(captor.capture());
        capturedUser = captor.getValue();
        assertEquals("1983517529@qq.com", capturedUser.getEmail());
        assertDoesNotThrow(() -> userRegisterService.registerUser(byPhoneNumber));

        verify(userMapper, times(3)).insertUser(captor.capture());
        capturedUser = captor.getValue();
        assertEquals("13517529835", capturedUser.getPhoneNumber());
    }

    @Test
    void testMissingRegister() {
        UserRegisterDTO missingLogin = mock(UserRegisterDTO.class, RETURNS_DEEP_STUBS);

        when(missingLogin.login()).thenReturn("");
        when(missingLogin.type()).thenReturn(UserLoginType.USERNAME);

        UserRegisterDTO fakeLoginDto = mock(UserRegisterDTO.class, RETURNS_DEEP_STUBS);

        when(fakeLoginDto.type()).thenReturn(UserLoginType.USERNAME);
        when(fakeLoginDto.login()).thenReturn("testuser");
        when(fakeLoginDto.password()).thenReturn("");

        BizException ex1 = assertThrows(BizException.class, () -> {
        userRegisterService.registerUser(missingLogin);
        });

        assertTrue(ex1.getMessage().contains("至少"));

        BizException ex2 = assertThrows(BizException.class, () -> {
            userRegisterService.registerUser(fakeLoginDto);
        });

        assertTrue(ex2.getMessage().contains("不能为空"));
    }

    @Test
    void testDuplicateRegister() {
        UserRegisterDTO dto = new UserRegisterDTO(UserLoginType.USERNAME, "duplicateUser", "password123");

        when(userMapper.countByUsername("duplicateUser")).thenReturn(1);

        BizException ex = assertThrows(BizException.class, () -> {
            userRegisterService.registerUser(dto);
        });

        assertTrue(ex.getMessage().contains("已存在"));
        verify(userMapper, never()).insertUser(any(User.class));
    }
}
