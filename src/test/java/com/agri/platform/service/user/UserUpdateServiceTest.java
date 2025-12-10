package com.agri.platform.service.user;

import com.agri.platform.DTO.UserUpdateDTO;
import com.agri.platform.entity.user.User;
import com.agri.platform.exception.BizException;
import com.agri.platform.mapper.user.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserUpdateServiceTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserUpdateService userUpdateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateUser_Success() {
        // 准备测试数据
        String userId = "test-user-id";
        UserUpdateDTO dto = new UserUpdateDTO(
                userId,
                "updated_username",
                "13800138000",
                "updated@example.com");

        // 模拟依赖行为
        User existingUser = new User();
        existingUser.setUserId(userId);
        when(userMapper.selectById(userId)).thenReturn(Optional.of(existingUser));

        // 执行测试方法
        userUpdateService.updateUser(dto);

        // 验证结果
        verify(userMapper).selectById(userId);
        verify(userMapper).updateByIdSelective(dto);
    }

    @Test
    void testUpdateUser_UserNotFound_ShouldThrowException() {
        // 准备测试数据
        String userId = "non-existent-user-id";
        UserUpdateDTO dto = new UserUpdateDTO(
                userId,
                "updated_username",
                null,
                null);

        // 模拟依赖行为
        when(userMapper.selectById(userId)).thenReturn(Optional.empty());

        // 执行测试并验证异常
        BizException exception = assertThrows(BizException.class, () -> {
            userUpdateService.updateUser(dto);
        });

        assertEquals("用户不存在！", exception.getMessage());
        verify(userMapper).selectById(userId);
        verify(userMapper, never()).updateByIdSelective(any());
    }

    @Test
    void testUpdateUser_PartialUpdate_Success() {
        // 准备测试数据 - 只更新用户名
        String userId = "test-user-id";
        UserUpdateDTO dto = new UserUpdateDTO(
                userId,
                "new_username",
                null, // 不更新电话号码
                null);

        // 模拟依赖行为
        User existingUser = new User();
        existingUser.setUserId(userId);
        when(userMapper.selectById(userId)).thenReturn(Optional.of(existingUser));

        // 执行测试方法
        userUpdateService.updateUser(dto);

        // 验证结果
        verify(userMapper).selectById(userId);
        verify(userMapper).updateByIdSelective(dto);
    }

    @Test
    void testUpdateUser_UpdateMultipleFields_Success() {
        // 准备测试数据 - 更新多个字段
        String userId = "test-user-id";
        UserUpdateDTO dto = new UserUpdateDTO(
                userId,
                "new_username",
                "13900139000",
                "new@example.com");

        // 模拟依赖行为
        User existingUser = new User();
        existingUser.setUserId(userId);
        when(userMapper.selectById(userId)).thenReturn(Optional.of(existingUser));

        // 执行测试方法
        userUpdateService.updateUser(dto);

        // 验证结果
        verify(userMapper).selectById(userId);
        verify(userMapper).updateByIdSelective(dto);
    }

    @Test
    void testUpdateUser_NullUserId_ShouldThrowException() {
        // 准备测试数据
        UserUpdateDTO dto = new UserUpdateDTO(
                null,
                "updated_username",
                null,
                null);

        // 执行测试并验证异常
        assertThrows(Exception.class, () -> {
            userUpdateService.updateUser(dto);
        });
    }
}