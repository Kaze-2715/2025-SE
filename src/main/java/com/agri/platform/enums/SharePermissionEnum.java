package com.agri.platform.enums;

/**
 * 报告分享权限
 * - VIEW_ONLY：仅查看
 * - EXPORT：可导出（也隐含可查看）
 */
public enum SharePermissionEnum {

    VIEW_ONLY("VIEW_ONLY", "仅查看"),
    EXPORT("EXPORT", "可导出");

    private final String code;
    private final String displayName;

    SharePermissionEnum(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() { return code; }

    public String getDisplayName() { return displayName; }

    /** 是否允许导出 */
    public boolean canExport() { return this == EXPORT; }

    /** 是否允许查看（所有权限均可查看） */
    public boolean canView() { return true; }

    /** 根据 code 解析，大小写不敏感；找不到返回 null */
    public static SharePermissionEnum fromCode(String code) {
        if (code == null) return null;
        for (SharePermissionEnum e : values()) {
            if (e.code.equalsIgnoreCase(code)) return e;
        }
        return null;
    }

    @Override
    public String toString() {
        return code;
    }
}

