package com.agri.platform.entity.user;

public class Permission {
    private String permissionId;
    private String permName;
    private String description;

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Permission() {
    }

    public Permission(String permissionId, String permName, String description) {
        this.permissionId = permissionId;
        this.permName = permName;
        this.description = description;
    }

}
