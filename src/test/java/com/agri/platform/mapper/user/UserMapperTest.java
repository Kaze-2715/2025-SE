package com.agri.platform.mapper.user;

import com.agri.platform.entity.user.User;
import com.agri.platform.entity.user.Role;
import com.agri.platform.entity.user.Permission;
import com.agri.platform.entity.user.User.AccountStatus;
import com.agri.platform.DTO.UserUpdateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsertUser() {
        // 准备测试数据
        String userId = "test-user-" + UUID.randomUUID().toString().substring(0, 8);
        User user = new User();
        user.setUserId(userId);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPhoneNumber("13800138000");
        user.setPasswordHash("hashed_password");
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setRegistrationTime(LocalDateTime.now());

        // 执行插入操作
        int result = userMapper.insertUser(user);
        assertThat(result).isEqualTo(1);

        // 验证插入成功
        Optional<User> insertedUser = userMapper.selectById(userId);
        assertThat(insertedUser).isPresent();
        assertThat(insertedUser.get().getUsername()).isEqualTo("testuser");
        assertThat(insertedUser.get().getEmail()).isEqualTo("test@example.com");
        assertThat(insertedUser.get().getPhoneNumber()).isEqualTo("13800138000");
    }

    @Test
    void testSelectById() {
        // 准备测试数据
        String userId = "test-user-" + UUID.randomUUID().toString().substring(0, 8);
        User user = new User();
        user.setUserId(userId);
        user.setUsername("selectByIdTest");
        user.setEmail("selectById@example.com");
        user.setPasswordHash("hashed_password");
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setRegistrationTime(LocalDateTime.now());
        userMapper.insertUser(user);

        // 执行查询操作
        Optional<User> foundUser = userMapper.selectById(userId);

        // 验证结果
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUserId()).isEqualTo(userId);
        assertThat(foundUser.get().getUsername()).isEqualTo("selectByIdTest");
        assertThat(foundUser.get().getEmail()).isEqualTo("selectById@example.com");
    }

    @Test
    void testSelectByUsername() {
        // 准备测试数据
        String userId = "test-user-" + UUID.randomUUID().toString().substring(0, 8);
        String username = "selectByUsernameTest";
        User user = new User();
        user.setUserId(userId);
        user.setUsername(username);
        user.setEmail("selectByUsername@example.com");
        user.setPasswordHash("hashed_password");
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setRegistrationTime(LocalDateTime.now());
        userMapper.insertUser(user);

        // 执行查询操作
        Optional<User> foundUser = userMapper.selectByUsername(username);

        // 验证结果
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo(username);
        assertThat(foundUser.get().getEmail()).isEqualTo("selectByUsername@example.com");
    }

    @Test
    void testSelectByEmail() {
        // 准备测试数据
        String userId = "test-user-" + UUID.randomUUID().toString().substring(0, 8);
        String email = "selectByEmail@example.com";
        User user = new User();
        user.setUserId(userId);
        user.setUsername("selectByEmailTest");
        user.setEmail(email);
        user.setPasswordHash("hashed_password");
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setRegistrationTime(LocalDateTime.now());
        userMapper.insertUser(user);

        // 执行查询操作
        Optional<User> foundUser = userMapper.selectByEmail(email);

        // 验证结果
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo(email);
        assertThat(foundUser.get().getUsername()).isEqualTo("selectByEmailTest");
    }

    @Test
    void testSelectByPhoneNumber() {
        // 准备测试数据
        String userId = "test-user-" + UUID.randomUUID().toString().substring(0, 8);
        String phoneNumber = "13900139000";
        User user = new User();
        user.setUserId(userId);
        user.setUsername("selectByPhoneTest");
        user.setEmail("selectByPhone@example.com");
        user.setPhoneNumber(phoneNumber);
        user.setPasswordHash("hashed_password");
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setRegistrationTime(LocalDateTime.now());
        userMapper.insertUser(user);

        // 执行查询操作
        Optional<User> foundUser = userMapper.selectByPhoneNumber(phoneNumber);

        // 验证结果
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(foundUser.get().getUsername()).isEqualTo("selectByPhoneTest");
    }

    @Test
    void testCountByUsername() {
        // 准备测试数据
        String userId = "test-user-" + UUID.randomUUID().toString().substring(0, 8);
        String username = "countByUsernameTest";
        User user = new User();
        user.setUserId(userId);
        user.setUsername(username);
        user.setEmail("countByUsername@example.com");
        user.setPasswordHash("hashed_password");
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setRegistrationTime(LocalDateTime.now());
        userMapper.insertUser(user);

        // 执行计数操作
        int count = userMapper.countByUsername(username);

        // 验证结果
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testCountByEmail() {
        // 准备测试数据
        String userId = "test-user-" + UUID.randomUUID().toString().substring(0, 8);
        String email = "countByEmail@example.com";
        User user = new User();
        user.setUserId(userId);
        user.setUsername("countByEmailTest");
        user.setEmail(email);
        user.setPasswordHash("hashed_password");
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setRegistrationTime(LocalDateTime.now());
        userMapper.insertUser(user);

        // 执行计数操作
        int count = userMapper.countByEmail(email);

        // 验证结果
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testCountByPhoneNumber() {
        // 准备测试数据
        String userId = "test-user-" + UUID.randomUUID().toString().substring(0, 8);
        String phoneNumber = "13700137000";
        User user = new User();
        user.setUserId(userId);
        user.setUsername("countByPhoneTest");
        user.setEmail("countByPhone@example.com");
        user.setPhoneNumber(phoneNumber);
        user.setPasswordHash("hashed_password");
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setRegistrationTime(LocalDateTime.now());
        userMapper.insertUser(user);

        // 执行计数操作
        int count = userMapper.countByPhoneNumber(phoneNumber);

        // 验证结果
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testUpdateAccountStatus() {
        // 准备测试数据
        String userId = "test-user-" + UUID.randomUUID().toString().substring(0, 8);
        User user = new User();
        user.setUserId(userId);
        user.setUsername("updateStatusTest");
        user.setEmail("updateStatus@example.com");
        user.setPasswordHash("hashed_password");
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setRegistrationTime(LocalDateTime.now());
        userMapper.insertUser(user);

        // 更新账户状态
        user.setAccountStatus(AccountStatus.FROZEN);
        userMapper.updateAccountStatus(user);

        // 验证更新成功
        Optional<User> updatedUser = userMapper.selectById(userId);
        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getAccountStatus()).isEqualTo(AccountStatus.FROZEN);
    }

    @Test
    void testUpdateLoginInfo() {
        // 准备测试数据
        String userId = "test-user-" + UUID.randomUUID().toString().substring(0, 8);
        User user = new User();
        user.setUserId(userId);
        user.setUsername("updateLoginTest");
        user.setEmail("updateLogin@example.com");
        user.setPasswordHash("hashed_password");
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setRegistrationTime(LocalDateTime.now());
        userMapper.insertUser(user);

        // 更新登录信息
        LocalDateTime loginTime = LocalDateTime.now();
        String loginIp = "192.168.1.100";
        user.setLastLoginTime(loginTime);
        user.setLastLoginIP(loginIp);
        user.setLoginFailCount(0);
        user.setLoginLockedUntil(null);
        userMapper.updateLoginInfo(user);

        // 验证更新成功
        Optional<User> updatedUser = userMapper.selectById(userId);
        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getLastLoginTime()).isEqualTo(loginTime);
        assertThat(updatedUser.get().getLastLoginIP()).isEqualTo(loginIp);
        assertThat(updatedUser.get().getLoginFailCount()).isEqualTo(0);
    }

    @Test
    void testUpdateByIdSelective() {
        // 准备测试数据
        String userId = "test-user-" + UUID.randomUUID().toString().substring(0, 8);
        User user = new User();
        user.setUserId(userId);
        user.setUsername("updateSelectiveTest");
        user.setEmail("updateSelective@example.com");
        user.setPasswordHash("hashed_password");
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setRegistrationTime(LocalDateTime.now());
        userMapper.insertUser(user);

        // 部分更新
        UserUpdateDTO updateDTO = new UserUpdateDTO(userId, "updatedUsername", "13900139001", "updated@example.com");
        int result = userMapper.updateByIdSelective(updateDTO);

        // 验证更新成功
        assertThat(result).isEqualTo(1);
        Optional<User> updatedUser = userMapper.selectById(userId);
        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getUsername()).isEqualTo("updatedUsername");
        assertThat(updatedUser.get().getEmail()).isEqualTo("updated@example.com");
        assertThat(updatedUser.get().getPhoneNumber()).isEqualTo("13900139001");
    }

    @Test
    void testListPermCodeByUserId() {
        // 注意：此测试依赖于预设的测试数据，已在data-h2.sql中配置
        // 用户u123具有role_user角色，该角色具有perm_farm_view权限

        // 执行查询操作
        List<String> permissions = userMapper.listPermCodeByUserId("u123");

        // 验证结果
        assertThat(permissions).isNotEmpty();
        assertThat(permissions).contains("perm_farm_view");
    }

    @Test
    void testInsertRole() {
        // 准备测试数据
        String roleId = "test-role-" + UUID.randomUUID().toString().substring(0, 8);
        Role role = new Role();
        role.setRoleId(roleId);
        role.setRoleName("TestRole");
        role.setDescription("Test role for unit testing");

        // 执行插入操作
        int result = userMapper.insertRole(role);
        assertThat(result).isEqualTo(1);

        // 验证插入成功
        Role insertedRole = userMapper.selectRoleById(roleId);
        assertThat(insertedRole).isNotNull();
        assertThat(insertedRole.getRoleName()).isEqualTo("TestRole");
        assertThat(insertedRole.getDescription()).isEqualTo("Test role for unit testing");
    }

    @Test
    void testSelectRoleById() {
        // 准备测试数据
        String roleId = "test-role-" + UUID.randomUUID().toString().substring(0, 8);
        Role role = new Role();
        role.setRoleId(roleId);
        role.setRoleName("SelectRoleTest");
        role.setDescription("Test role for select by id");
        userMapper.insertRole(role);

        // 执行查询操作
        Role foundRole = userMapper.selectRoleById(roleId);

        // 验证结果
        assertThat(foundRole).isNotNull();
        assertThat(foundRole.getRoleId()).isEqualTo(roleId);
        assertThat(foundRole.getRoleName()).isEqualTo("SelectRoleTest");
    }

    @Test
    void testCountByRoleName() {
        // 准备测试数据
        String roleId = "test-role-" + UUID.randomUUID().toString().substring(0, 8);
        String roleName = "CountRoleTest";
        Role role = new Role();
        role.setRoleId(roleId);
        role.setRoleName(roleName);
        role.setDescription("Test role for count by name");
        userMapper.insertRole(role);

        // 执行计数操作
        int count = userMapper.countByRoleName(roleName);

        // 验证结果
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testInsertUserRole() {
        // 准备测试数据
        String userId = "test-user-" + UUID.randomUUID().toString().substring(0, 8);
        String roleId = "test-role-" + UUID.randomUUID().toString().substring(0, 8);

        User user = new User();
        user.setUserId(userId);
        user.setUsername("insertUserRoleTest");
        user.setEmail("insertUserRole@example.com");
        user.setPasswordHash("hashed_password");
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setRegistrationTime(LocalDateTime.now());
        userMapper.insertUser(user);

        Role role = new Role();
        role.setRoleId(roleId);
        role.setRoleName("InsertUserRoleTest");
        role.setDescription("Test role for insert user role");
        userMapper.insertRole(role);

        // 执行插入用户角色关联操作
        int result = userMapper.insertUserRole(userId, roleId);
        assertThat(result).isEqualTo(1);

        // 验证插入成功
        int count = userMapper.countUserRole(userId, roleId);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testAddPermission() {
        // 准备测试数据
        String permissionId = "test-perm-" + UUID.randomUUID().toString().substring(0, 8);
        Permission permission = new Permission();
        permission.setPermissionId(permissionId);
        permission.setPermName("test.permission.create");
        permission.setDescription("Test permission for unit testing");

        // 执行插入操作
        int result = userMapper.addPermission(permission);
        assertThat(result).isEqualTo(1);

        // 验证插入成功
        Permission insertedPermission = userMapper.selectPermissionById(permissionId);
        assertThat(insertedPermission).isNotNull();
        assertThat(insertedPermission.getPermName()).isEqualTo("test.permission.create");
        assertThat(insertedPermission.getDescription()).isEqualTo("Test permission for unit testing");
    }

    @Test
    void testSelectPermissionById() {
        // 准备测试数据
        String permissionId = "test-perm-" + UUID.randomUUID().toString().substring(0, 8);
        Permission permission = new Permission();
        permission.setPermissionId(permissionId);
        permission.setPermName("select.perm.test");
        permission.setDescription("Test permission for select by id");
        userMapper.addPermission(permission);

        // 执行查询操作
        Permission foundPermission = userMapper.selectPermissionById(permissionId);

        // 验证结果
        assertThat(foundPermission).isNotNull();
        assertThat(foundPermission.getPermissionId()).isEqualTo(permissionId);
        assertThat(foundPermission.getPermName()).isEqualTo("select.perm.test");
    }

    @Test
    void testCountByPermName() {
        // 准备测试数据
        String permissionId = "test-perm-" + UUID.randomUUID().toString().substring(0, 8);
        String permName = "count.perm.test";
        Permission permission = new Permission();
        permission.setPermissionId(permissionId);
        permission.setPermName(permName);
        permission.setDescription("Test permission for count by name");
        userMapper.addPermission(permission);

        // 执行计数操作
        int count = userMapper.countByPermName(permName);

        // 验证结果
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testInsertRolePermission() {
        // 准备测试数据
        String roleId = "test-role-" + UUID.randomUUID().toString().substring(0, 8);
        String permissionId = "test-perm-" + UUID.randomUUID().toString().substring(0, 8);

        Role role = new Role();
        role.setRoleId(roleId);
        role.setRoleName("InsertRolePermTest");
        role.setDescription("Test role for insert role permission");
        userMapper.insertRole(role);

        Permission permission = new Permission();
        permission.setPermissionId(permissionId);
        permission.setPermName("insert.role.perm.test");
        permission.setDescription("Test permission for insert role permission");
        userMapper.addPermission(permission);

        // 执行插入角色权限关联操作
        int result = userMapper.insertRolePermission(roleId, permissionId);
        assertThat(result).isEqualTo(1);

        // 验证插入成功
        int count = userMapper.countRolePermission(roleId, permissionId);
        assertThat(count).isEqualTo(1);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testListUserRole() {
        // 执行查询操作
        // 注意：此测试依赖于预设的测试数据，已在data-h2.sql中配置
        List<Object> userRoles = (List<Object>) (List<?>) userMapper.listUserRole();

        // 验证结果不为空
        assertThat(userRoles).isNotNull();
    }

    @Test
    @SuppressWarnings("unchecked")
    void testListRolePermission() {
        // 执行查询操作
        List<Object> rolePermissions = (List<Object>) (List<?>) userMapper.listRolePermission();

        // 验证结果不为空
        assertThat(rolePermissions).isNotNull();
    }

    @Test
    void testListAllPermissions() {
        // 执行查询操作
        List<String> permissions = userMapper.listAllPermissions();

        // 验证结果不为空
        assertThat(permissions).isNotNull();
    }
}