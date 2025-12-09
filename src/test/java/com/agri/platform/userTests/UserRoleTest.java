package com.agri.platform.userTests;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import com.agri.platform.controller.user.UserRolePermController;
import com.agri.platform.mapper.user.UserMapper;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRoleTest {
     @Autowired
    private UserRolePermController controller;

    @Autowired
    private UserMapper userMapper;

    static String roleId;           // 临时存主键，供多步使用

    /* 要用的常量 */
    final String TEST_USER = "974a8920-e829-48df-b058-e6c724e1bfa0";
    final String ROLE_NAME = "TEST_ROLE";

    @Test
    void testRoleLifecycle() {
        /* 1. 创建角色 */
        controller.addRole(ROLE_NAME, "初始描述");
        String roleId = userMapper.selectRoleIdByRoleName(ROLE_NAME);
        assertThat(roleId).isNotNull();

        /* 2. 绑定给用户 */
        userMapper.insertUserRole(TEST_USER, roleId);
        boolean hasRole = controller.listUserRoles()
                .stream()
                .anyMatch(ur -> ROLE_NAME.equals(ur.roleName()));
        assertThat(hasRole).isTrue();

        /* 3. 更新角色描述 */
        controller.updateRole(ROLE_NAME, "更新后描述");
        String newDesc = userMapper.selectRoleById(roleId).getDescription();
        assertThat(newDesc).isEqualTo("更新后描述");

        /* 4. 删除角色（级联） */
        controller.deleteRole(ROLE_NAME);
        assertThat(userMapper.selectRoleIdByRoleName(ROLE_NAME)).isNull();

        /* 5. 断言用户已没有该角色 */
        boolean stillHas = controller.listUserRoles()
                .stream()
                .anyMatch(ur -> ROLE_NAME.equals(ur.roleName()));
        assertThat(stillHas).isFalse();
    }
}
