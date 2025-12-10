package com.agri.platform.service.user;

import com.agri.platform.DTO.RolePermDTO;
import com.agri.platform.DTO.UserRoleDTO;
import com.agri.platform.entity.user.Permission;
import com.agri.platform.entity.user.Role;
import com.agri.platform.entity.user.User;
import com.agri.platform.exception.BizException;
import com.agri.platform.mapper.user.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserRolePermServiceTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserRolePermService userRolePermService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListUserRoles() {
        // 准备测试数据
        UserRoleDTO userRoleDTO = new UserRoleDTO("userId", "username", "roleId", "roleName");
        List<UserRoleDTO> expectedList = Arrays.asList(userRoleDTO);

        // 模拟mapper行为
        when(userMapper.listUserRole()).thenReturn(expectedList);

        // 执行测试方法
        List<UserRoleDTO> result = userRolePermService.listUserRoles();

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("userId", result.get(0).userId());
        assertEquals("username", result.get(0).username());
        assertEquals("roleId", result.get(0).roleId());
        assertEquals("roleName", result.get(0).roleName());

        // 验证方法调用
        verify(userMapper, times(1)).listUserRole();
    }

    @Test
    void testListRolePerms() {
        // 准备测试数据
        RolePermDTO rolePermDTO = new RolePermDTO("roleId", "roleName", "permId", "permName");
        List<RolePermDTO> expectedList = Arrays.asList(rolePermDTO);

        // 模拟mapper行为
        when(userMapper.listRolePermission()).thenReturn(expectedList);

        // 执行测试方法
        List<RolePermDTO> result = userRolePermService.listRolePerms();

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("roleId", result.get(0).roleId());
        assertEquals("roleName", result.get(0).roleName());
        assertEquals("permId", result.get(0).permId());
        assertEquals("permName", result.get(0).permName());

        // 验证方法调用
        verify(userMapper, times(1)).listRolePermission();
    }

    @Test
    void testListPermissions() {
        // 准备测试数据
        List<String> expectedList = Arrays.asList("perm1", "perm2");

        // 模拟mapper行为
        when(userMapper.listAllPermissions()).thenReturn(expectedList);

        // 执行测试方法
        List<String> result = userRolePermService.listPermissions();

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("perm1"));
        assertTrue(result.contains("perm2"));

        // 验证方法调用
        verify(userMapper, times(1)).listAllPermissions();
    }

    @Test
    void testAddRole_Success() {
        // 准备测试数据
        String roleName = "testRole";
        String description = "test description";

        // 模拟mapper行为
        when(userMapper.countByRoleName(roleName)).thenReturn(0); // 角色不存在
        when(userMapper.insertRole(any(Role.class))).thenReturn(1);

        // 执行测试方法
        assertDoesNotThrow(() -> userRolePermService.addRole(roleName, description));

        // 验证方法调用
        verify(userMapper, times(1)).countByRoleName(roleName);
        verify(userMapper, times(1)).insertRole(any(Role.class));
    }

    @Test
    void testAddRole_RoleAlreadyExists() {
        // 准备测试数据
        String roleName = "existingRole";
        String description = "test description";

        // 模拟mapper行为
        when(userMapper.countByRoleName(roleName)).thenReturn(1); // 角色已存在

        // 执行测试方法并验证异常
        BizException exception = assertThrows(BizException.class, () -> {
            userRolePermService.addRole(roleName, description);
        });

        assertEquals("角色名称已存在", exception.getMessage());

        // 验证方法调用
        verify(userMapper, times(1)).countByRoleName(roleName);
        verify(userMapper, times(0)).insertRole(any(Role.class));
    }

    @Test
    void testDeleteRoleByName_Success() {
        // 准备测试数据
        String roleName = "testRole";
        String roleId = "roleId123";

        // 模拟mapper行为
        when(userMapper.selectRoleIdByRoleName(roleName)).thenReturn(roleId);
        when(userMapper.deleteRoleById(roleId)).thenReturn(1);
        when(userMapper.deleteRolePermissionByRoleId(roleId)).thenReturn(1);
        when(userMapper.deleteUserRoleByRoleId(roleId)).thenReturn(1);

        // 执行测试方法
        assertDoesNotThrow(() -> userRolePermService.deleteRoleByName(roleName));

        // 验证方法调用
        verify(userMapper, times(1)).selectRoleIdByRoleName(roleName);
        verify(userMapper, times(1)).deleteRoleById(roleId);
        verify(userMapper, times(1)).deleteRolePermissionByRoleId(roleId);
        verify(userMapper, times(1)).deleteUserRoleByRoleId(roleId);
    }

    @Test
    void testDeleteRoleByName_RoleNotFound() {
        // 准备测试数据
        String roleName = "nonExistentRole";

        // 模拟mapper行为
        when(userMapper.selectRoleIdByRoleName(roleName)).thenReturn(null); // 角色不存在

        // 执行测试方法并验证异常
        BizException exception = assertThrows(BizException.class, () -> {
            userRolePermService.deleteRoleByName(roleName);
        });

        assertEquals("角色不存在", exception.getMessage());

        // 验证方法调用
        verify(userMapper, times(1)).selectRoleIdByRoleName(roleName);
        verify(userMapper, times(0)).deleteRoleById(anyString());
        verify(userMapper, times(0)).deleteRolePermissionByRoleId(anyString());
        verify(userMapper, times(0)).deleteUserRoleByRoleId(anyString());
    }

    @Test
    void testUpdateRole() {
        // 准备测试数据
        String roleName = "testRole";
        String newDescription = "new description";

        // 模拟mapper行为
        when(userMapper.updateRole(roleName, newDescription)).thenReturn(1);

        // 执行测试方法
        userRolePermService.updateRole(roleName, newDescription);

        // 验证方法调用
        verify(userMapper, times(1)).updateRole(roleName, newDescription);
    }

    @Test
    void testAddPermission_Success() {
        // 准备测试数据
        String permissionName = "testPermission";
        String description = "test description";

        // 模拟mapper行为
        when(userMapper.countByRoleName(permissionName)).thenReturn(0); // 权限不存在
        when(userMapper.addPermission(any(Permission.class))).thenReturn(1);

        // 执行测试方法
        assertDoesNotThrow(() -> userRolePermService.addPermission(permissionName, description));

        // 验证方法调用
        verify(userMapper, times(1)).countByRoleName(permissionName);
        verify(userMapper, times(1)).addPermission(any(Permission.class));
    }

    @Test
    void testAddPermission_PermissionAlreadyExists() {
        // 准备测试数据
        String permissionName = "existingPermission";
        String description = "test description";

        // 模拟mapper行为
        when(userMapper.countByRoleName(permissionName)).thenReturn(1); // 权限已存在

        // 执行测试方法并验证异常
        BizException exception = assertThrows(BizException.class, () -> {
            userRolePermService.addPermission(permissionName, description);
        });

        assertEquals("权限名称已存在", exception.getMessage());

        // 验证方法调用
        verify(userMapper, times(1)).countByRoleName(permissionName);
        verify(userMapper, times(0)).addPermission(any(Permission.class));
    }

    @Test
    void testDeletePermissionByName_Success() {
        // 准备测试数据
        String permissionName = "testPermission";
        String permissionId = "permId123";

        // 模拟mapper行为
        when(userMapper.selectPermissionIdByPermissionName(permissionName)).thenReturn(permissionId);
        when(userMapper.deletePermissionById(permissionId)).thenReturn(1);
        when(userMapper.deleteRolePermissionByPermissionId(permissionId)).thenReturn(1);

        // 执行测试方法
        assertDoesNotThrow(() -> userRolePermService.deletePermissionByName(permissionName));

        // 验证方法调用
        verify(userMapper, times(1)).selectPermissionIdByPermissionName(permissionName);
        verify(userMapper, times(1)).deletePermissionById(permissionId);
        verify(userMapper, times(1)).deleteRolePermissionByPermissionId(permissionId);
    }

    @Test
    void testDeletePermissionByName_PermissionNotFound() {
        // 准备测试数据
        String permissionName = "nonExistentPermission";

        // 模拟mapper行为
        when(userMapper.selectPermissionIdByPermissionName(permissionName)).thenReturn(null); // 权限不存在

        // 执行测试方法并验证异常
        BizException exception = assertThrows(BizException.class, () -> {
            userRolePermService.deletePermissionByName(permissionName);
        });

        assertEquals("权限不存在", exception.getMessage());

        // 验证方法调用
        verify(userMapper, times(1)).selectPermissionIdByPermissionName(permissionName);
        verify(userMapper, times(0)).deletePermissionById(anyString());
        verify(userMapper, times(0)).deleteRolePermissionByPermissionId(anyString());
    }

    @Test
    void testUpdatePermission() {
        // 准备测试数据
        String permissionName = "testPermission";
        String newDescription = "new description";

        // 模拟mapper行为
        when(userMapper.updatePermission(permissionName, newDescription)).thenReturn(1);

        // 执行测试方法
        userRolePermService.updatePermission(permissionName, newDescription);

        // 验证方法调用
        verify(userMapper, times(1)).updatePermission(permissionName, newDescription);
    }

    @Test
    void testGrantRoleToUser_Success() {
        // 准备测试数据
        String userId = "userId123";
        String roleName = "testRole";
        String roleId = "roleId123";

        // 创建一个User对象用于模拟
        User mockUser = new User();
        mockUser.setUserId(userId);

        // 模拟mapper行为
        when(userMapper.selectRoleIdByRoleName(roleName)).thenReturn(roleId);
        when(userMapper.selectById(userId)).thenReturn(Optional.of(mockUser)); // 用户存在
        when(userMapper.countUserRole(userId, roleId)).thenReturn(0); // 用户还没有该角色
        when(userMapper.insertUserRole(userId, roleId)).thenReturn(1);

        // 执行测试方法
        assertDoesNotThrow(() -> userRolePermService.grantRoleToUser(userId, roleName));

        // 验证方法调用
        verify(userMapper, times(1)).selectRoleIdByRoleName(roleName);
        verify(userMapper, times(1)).selectById(userId);
        verify(userMapper, times(1)).countUserRole(userId, roleId);
        verify(userMapper, times(1)).insertUserRole(userId, roleId);
    }

    @Test
    void testGrantRoleToUser_RoleNotFound() {
        // 准备测试数据
        String userId = "userId123";
        String roleName = "nonExistentRole";

        // 模拟mapper行为
        when(userMapper.selectRoleIdByRoleName(roleName)).thenReturn(null); // 角色不存在

        // 执行测试方法并验证异常
        BizException exception = assertThrows(BizException.class, () -> {
            userRolePermService.grantRoleToUser(userId, roleName);
        });

        assertEquals("角色不存在", exception.getMessage());

        // 验证方法调用
        verify(userMapper, times(1)).selectRoleIdByRoleName(roleName);
        verify(userMapper, times(0)).selectById(anyString());
        verify(userMapper, times(0)).countUserRole(anyString(), anyString());
        verify(userMapper, times(0)).insertUserRole(anyString(), anyString());
    }

    @Test
    void testGrantRoleToUser_UserNotFound() {
        // 准备测试数据
        String userId = "nonExistentUserId";
        String roleName = "testRole";
        String roleId = "roleId123";

        // 模拟mapper行为
        when(userMapper.selectRoleIdByRoleName(roleName)).thenReturn(roleId);
        when(userMapper.selectById(userId)).thenReturn(Optional.empty()); // 用户不存在

        // 执行测试方法并验证异常
        BizException exception = assertThrows(BizException.class, () -> {
            userRolePermService.grantRoleToUser(userId, roleName);
        });

        assertEquals("用户不存在", exception.getMessage());

        // 验证方法调用
        verify(userMapper, times(1)).selectRoleIdByRoleName(roleName);
        verify(userMapper, times(1)).selectById(userId);
        verify(userMapper, times(0)).countUserRole(anyString(), anyString());
        verify(userMapper, times(0)).insertUserRole(anyString(), anyString());
    }

    @Test
    void testBindPermissionToRole_Success() {
        // 准备测试数据
        String roleName = "testRole";
        String permName = "testPermission";
        String roleId = "roleId123";
        String permId = "permId123";

        // 模拟mapper行为
        when(userMapper.selectRoleIdByRoleName(roleName)).thenReturn(roleId); // 角色存在
        when(userMapper.selectIdByPermName(permName)).thenReturn(Optional.of(permId)); // 权限存在
        when(userMapper.countRolePermission(roleId, permId)).thenReturn(0); // 还未绑定
        when(userMapper.insertRolePermission(roleId, permId)).thenReturn(1);

        // 执行测试方法
        assertDoesNotThrow(() -> userRolePermService.bindPermissionToRole(roleName, permName));

        // 验证方法调用
        verify(userMapper, times(1)).selectRoleIdByRoleName(roleName);
        verify(userMapper, times(1)).selectIdByPermName(permName);
        verify(userMapper, times(1)).countRolePermission(roleId, permId);
        verify(userMapper, times(1)).insertRolePermission(roleId, permId);
    }

    @Test
    void testBindPermissionToRole_RoleNotFound() {
        // 准备测试数据
        String roleName = "nonExistentRole";
        String permName = "testPermission";

        // 模拟mapper行为
        when(userMapper.selectRoleIdByRoleName(roleName)).thenReturn(null); // 角色不存在

        // 执行测试方法并验证异常
        BizException exception = assertThrows(BizException.class, () -> {
            userRolePermService.bindPermissionToRole(roleName, permName);
        });

        assertEquals("角色不存在", exception.getMessage());

        // 验证方法调用
        verify(userMapper, times(1)).selectRoleIdByRoleName(roleName);
        verify(userMapper, times(0)).selectIdByPermName(anyString());
        verify(userMapper, times(0)).countRolePermission(anyString(), anyString());
        verify(userMapper, times(0)).insertRolePermission(anyString(), anyString());
    }

    @Test
    void testBindPermissionToRole_PermissionNotFound() {
        // 准备测试数据
        String roleName = "testRole";
        String permName = "nonExistentPermission";
        String roleId = "roleId123";

        // 模拟mapper行为
        when(userMapper.selectRoleIdByRoleName(roleName)).thenReturn(roleId); // 角色存在
        when(userMapper.selectIdByPermName(permName)).thenReturn(Optional.empty()); // 权限不存在

        // 执行测试方法并验证异常
        BizException exception = assertThrows(BizException.class, () -> {
            userRolePermService.bindPermissionToRole(roleName, permName);
        });

        assertEquals("权限名称无效", exception.getMessage());

        // 验证方法调用
        verify(userMapper, times(1)).selectRoleIdByRoleName(roleName);
        verify(userMapper, times(1)).selectIdByPermName(permName);
        verify(userMapper, times(0)).countRolePermission(anyString(), anyString());
        verify(userMapper, times(0)).insertRolePermission(anyString(), anyString());
    }

    @Test
    void testBindPermissionToRole_AlreadyBound() {
        // 准备测试数据
        String roleName = "testRole";
        String permName = "testPermission";
        String roleId = "roleId123";
        String permId = "permId123";

        // 模拟mapper行为
        when(userMapper.selectRoleIdByRoleName(roleName)).thenReturn(roleId); // 角色存在
        when(userMapper.selectIdByPermName(permName)).thenReturn(Optional.of(permId)); // 权限存在
        when(userMapper.countRolePermission(roleId, permId)).thenReturn(1); // 已经绑定

        // 执行测试方法（应该不会抛出异常，因为是幂等操作）
        assertDoesNotThrow(() -> userRolePermService.bindPermissionToRole(roleName, permName));

        // 验证方法调用
        verify(userMapper, times(1)).selectRoleIdByRoleName(roleName);
        verify(userMapper, times(1)).selectIdByPermName(permName);
        verify(userMapper, times(1)).countRolePermission(roleId, permId);
        verify(userMapper, times(0)).insertRolePermission(anyString(), anyString());
    }
}