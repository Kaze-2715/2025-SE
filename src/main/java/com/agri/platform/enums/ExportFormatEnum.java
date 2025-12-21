package com.agri.platform.enums;

/**
 * 导出格式枚举
 * - 提供文件扩展名与内容类型映射
 */
public enum ExportFormatEnum {

    PDF("pdf", "application/pdf"),
    XLSX("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    CSV("csv", "text/csv");

    private final String fileExtension;
    private final String contentType;

    ExportFormatEnum(String fileExtension, String contentType) {
        this.fileExtension = fileExtension;
        this.contentType = contentType;
    }

    /** 文件扩展名（不含点） */
    public String getFileExtension() {
        return fileExtension;
    }

    /** HTTP Content-Type */
    public String getContentType() {
        return contentType;
    }

    @Override
    public String toString() {
        return name();
    }

    /**
     * 通过名称/扩展名/Content-Type 解析，大小写不敏感；无法解析返回 null
     */
    public static ExportFormatEnum fromString(String v) {
        if (v == null || v.isEmpty()) return null;
        for (ExportFormatEnum f : values()) {
            if (f.name().equalsIgnoreCase(v)
                || f.fileExtension.equalsIgnoreCase(v)
                || f.contentType.equalsIgnoreCase(v)) {
                return f;
            }
        }
        return null;
    }

    /**
     * 根据基础名生成带后缀的文件名
     */
    public String toFilename(String baseName) {
        String base = (baseName == null || baseName.isEmpty()) ? "report" : baseName;
        return base + "." + fileExtension;
    }
}
