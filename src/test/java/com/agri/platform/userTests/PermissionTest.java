package com.agri.platform.userTests;

import com.agri.platform.mapper.user.UserMapper;
import com.agri.platform.controller.user.UserRolePermController;
import com.agri.platform.entity.user.Permission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

@SpringBootTest
@Transactional
public class PermissionTest {
    @Autowired
    private UserRolePermController controller; // 直接用 HTTP 入口测

    @Autowired
    private UserMapper permissionMapper; // 底层断言用

    /* 测试常量 */
    private static final String PERM_NAME = "TEST_PERM";
    private static final String NEW_DESC = "修改后描述";

    @Test
    void testPermissionLifecycle() {
        /* 1. 新增权限 */
        controller.addPermission(PERM_NAME, "初始描述");
        String permId = permissionMapper.selectPermissionIdByPermissionName(PERM_NAME);
        assertThat(permId).isNotNull();

        /* 2. 查询：列表里包含刚新增的权限 */
        boolean exists = controller.listPermissions()
                .stream()
                .anyMatch(p -> PERM_NAME.equals(p));
        assertThat(exists).isTrue();

        /* 3. 更新描述 */
        controller.updatePermission(PERM_NAME, NEW_DESC);
        Permission updated = permissionMapper.selectPermissionById(permId);
        assertThat(updated.getDescription()).isEqualTo(NEW_DESC);

        /* 4. 删除权限 */
        controller.deletePermission(PERM_NAME);
        assertThat(permissionMapper.countByPermName(PERM_NAME)).isZero();
    }

    @Test
    @Transactional
    void testGrantRoleToUser() {
        // 事前准备：角色必须存在
        String roleName = "TEST_ROLE";
        controller.addRole(roleName, "desc");

        // 给用户授予角色
        String testUser = "974a8920-e829-48df-b058-e6c724e1bfa0";
        controller.grantRoleToUser(testUser, roleName);

        // 断言用户已有该角色
        boolean hasRole = controller.listUserRoles()
                .stream()
                .anyMatch(ur -> testUser.equals(ur.userId())
                        && roleName.equals(ur.roleName()));
        assertThat(hasRole).isTrue();
    }

    @Test
    @Transactional
    void testBindPermissionToRole() {
        // 预备数据
        controller.addRole("TEST_ROLE", "desc");
        controller.addPermission("user:add", "新增用户");

        // 绑定
        controller.bindPermissionToRole("TEST_ROLE", "user:add");

        // 断言角色已拥有该权限
        String roleId = permissionMapper.selectRoleIdByRoleName("TEST_ROLE");
        List<String> permNames = permissionMapper.listPermNameByRoleId(roleId);
        assertThat(permNames).contains("user:add");

        // 重复绑定不报错（幂等）
        controller.bindPermissionToRole("TEST_ROLE", "user:add");
        permNames = permissionMapper.listPermNameByRoleId(roleId);
        assertThat(permNames).contains("user:add");
    }
}
