package com.agri.platform.controller.user;

import org.springframework.web.bind.annotation.RestController;

import com.agri.platform.service.user.UserRolePermService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import com.agri.platform.DTO.RolePermDTO;

import com.agri.platform.DTO.UserRoleDTO;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserRolePermController {
    private final UserRolePermService userRolePermService;

    @GetMapping("/user-roles")
    public List<UserRoleDTO> listUserRoles() {
        return userRolePermService.listUserRoles();
    }

    @GetMapping("/role-perms")
    public List<RolePermDTO> listRolePerms() {
        return userRolePermService.listRolePerms();
    }

    @GetMapping("/permissions")
    public List<String> listPermissions() {
        return userRolePermService.listPermissions();
    }

    @PostMapping("/role")
    public ResponseEntity<Void> addRole(@RequestParam String roleName, @RequestParam String description) {
        userRolePermService.addRole(roleName, description);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/role")
    public ResponseEntity<Void> deleteRole(@RequestParam String roleName) {
        userRolePermService.deleteRoleByName(roleName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/role")
    public ResponseEntity<Void> updateRole(@RequestParam String roleName, @RequestParam String newDescription) {
        userRolePermService.updateRole(roleName, newDescription);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/permission")
    public ResponseEntity<Void> addPermission(@RequestParam String permissionName, @RequestParam String description) {
        userRolePermService.addPermission(permissionName, description);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/permission")
    public ResponseEntity<Void> deletePermission(@RequestParam String permissionName) {
        userRolePermService.deletePermissionByName(permissionName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/permission")
    public ResponseEntity<Void> updatePermission(@RequestParam String permissionName,
            @RequestParam String newDescription) {
        userRolePermService.updatePermission(permissionName, newDescription);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/user-role")
    public ResponseEntity<Void> grantRoleToUser(@RequestParam String userId, @RequestParam String roleName) {
        userRolePermService.grantRoleToUser(userId, roleName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/role-permission")
    public ResponseEntity<Void> bindPermissionToRole(@RequestParam String roleName,
            @RequestParam String permName) {
        userRolePermService.bindPermissionToRole(roleName, permName);
        return ResponseEntity.ok().build();
    }
}