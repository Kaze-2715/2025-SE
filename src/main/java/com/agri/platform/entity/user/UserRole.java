package com.agri.platform.entity.user;

import java.time.LocalDateTime;

public class UserRole {
    private String userId;
    private String roleId;
    private LocalDateTime assignedTime;
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public LocalDateTime getAssignedTime() {
        return assignedTime;
    }
    public void setAssignedTime(LocalDateTime assignedTime) {
        this.assignedTime = assignedTime;
    }
    public UserRole(String userId, String roleId, LocalDateTime assignedTime) {
        this.userId = userId;
        this.roleId = roleId;
        this.assignedTime = assignedTime;
    }
    public UserRole() {
    }

    
}
