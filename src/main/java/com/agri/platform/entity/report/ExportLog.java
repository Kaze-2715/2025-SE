package com.agri.platform.entity.report;

import java.time.LocalDateTime;

/**
 * 导出日志表
 */
public class ExportLog {

    private Long id;
    private Long reportId;
    private Long exporterId;
    private String format;          // PDF/EXCEL
    private String fileName;        // 生成的文件名或URL
    private LocalDateTime exportedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getReportId() { return reportId; }
    public void setReportId(Long reportId) { this.reportId = reportId; }
    public Long getExporterId() { return exporterId; }
    public void setExporterId(Long exporterId) { this.exporterId = exporterId; }
    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public LocalDateTime getExportedAt() { return exportedAt; }
    public void setExportedAt(LocalDateTime exportedAt) { this.exportedAt = exportedAt; }
}
