package com.agri.platform.enums;

import java.util.Arrays;

/**
 * 报告类型枚举
 * 对应系统中不同角色生成的报告类型（文档功能-1 ~ 功能-6）
 */
public enum ReportTypeEnum {

    /** 农场主生成的种植记录报告 */
    PLANTING_RECORD("PLANTING_RECORD", "种植记录报告"),

    /** 系统自动生成的资源利用报告 */
    RESOURCE_USAGE("RESOURCE_USAGE", "资源利用报告"),

    /** 农场主生成的产量报告 */
    YIELD("YIELD", "产量报告"),

    /** 技术顾问生成的技术支持记录报告 */
    TECH_SUPPORT("TECH_SUPPORT", "技术支持记录报告"),

    /** 数据分析师生成的市场分析报告 */
    MARKET_ANALYSIS("MARKET_ANALYSIS", "市场分析报告"),

    /** 系统管理员生成的全平台统计报告 */
    PLATFORM_STATISTICS("PLATFORM_STATISTICS", "平台统计报告");

    /** 类型编码（供程序逻辑使用） */
    private final String code;

    /** 类型名称（中文显示名） */
    private final String displayName;

    ReportTypeEnum(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * 根据 code 获取枚举
     * @param code 报告类型编码
     * @return ReportTypeEnum or null
     */
    public static ReportTypeEnum fromCode(String code) {
        return Arrays.stream(values())
                     .filter(e -> e.code.equalsIgnoreCase(code))
                     .findFirst()
                     .orElse(null);
    }
}
