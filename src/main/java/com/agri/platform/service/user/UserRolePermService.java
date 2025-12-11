package com.agri.platform.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import com.agri.platform.DTO.RolePermDTO;
import com.agri.platform.DTO.UserRoleDTO;
import com.agri.platform.exception.BizException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.agri.platform.mapper.user.UserMapper;
import com.agri.platform.entity.user.Role;
import com.agri.platform.entity.user.Permission;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRolePermService {
    private final UserMapper userMapper;

    public List<UserRoleDTO> listUserRoles() {
        return userMapper.listUserRole();
    }

    public List<RolePermDTO> listRolePerms() {
        return userMapper.listRolePermission();
    }

    public List<String> listPermissions() {
        return userMapper.listAllPermissions();
    }

    @Transactional
    public void addRole(String roleName, String description) {
        if (userMapper.countByRoleName(roleName) > 0) {
            throw new BizException("角色名称已存在");
        }

        String roleId = UUID.randomUUID().toString();
        Role role = new Role();
        role.setRoleId(roleId);
        role.setRoleName(roleName);
        role.setDescription(description);
        userMapper.insertRole(role);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleByName(String roleName) {
        String roleId = userMapper.selectRoleIdByRoleName(roleName);
        if (roleId == null) {
            throw new BizException("角色不存在");
        }
        userMapper.deleteRoleById(roleId);
        userMapper.deleteRolePermissionByRoleId(roleId);
        userMapper.deleteUserRoleByRoleId(roleId);
    }

    public void updateRole(String roleName, String newDescription) {
        userMapper.updateRole(roleName, newDescription);
    }

    public void addPermission(String permissionName, String description) {
        if (userMapper.countByRoleName(permissionName) > 0) {
            throw new BizException("权限名称已存在");
        }
        String permissionId = UUID.randomUUID().toString();
        Permission permission = new Permission();
        permission.setPermissionId(permissionId);
        permission.setPermName(permissionName);
        permission.setDescription(description);
        userMapper.addPermission(permission);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletePermissionByName(String permissionName) {
        String permissionId = userMapper.selectPermissionIdByPermissionName(permissionName);
        if (permissionId == null) {
            throw new BizException("权限不存在");
        }
        userMapper.deletePermissionById(permissionId);
        userMapper.deleteRolePermissionByPermissionId(permissionId);
    }

    public void updatePermission(String permissionName, String newDescription) {
        userMapper.updatePermission(permissionName, newDescription);
    }

    @Transactional
    public void grantRoleToUser(String userId, String roleName) {
        String roleId = userMapper.selectRoleIdByRoleName(roleName);
        if (roleId == null) {
            throw new BizException("角色不存在");
        }
        if (userMapper.selectById(userId).isEmpty()) {
            throw new BizException("用户不存在");
        }
        if (userMapper.countUserRole(userId, roleId) > 0) {
            return;
        }
        userMapper.insertUserRole(userId, roleId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void bindPermissionToRole(String roleName, String permName) {
        // 1. 角色存在性
        String roleId = userMapper.selectRoleIdByRoleName(roleName);
        if (roleId == null)
            throw new BizException("角色不存在");

        // 2. 权限存在性
        String permId = userMapper.selectIdByPermName(permName)
                .orElseThrow(() -> new BizException("权限名称无效"));

        // 3. 幂等：已绑定则直接返回
        if (userMapper.countRolePermission(roleId, permId) > 0) {
            return;
        }

        // 4. 单条绑定
        userMapper.insertRolePermission(roleId, permId);
    }

}
