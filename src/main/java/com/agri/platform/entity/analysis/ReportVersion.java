// src/main/java/com/agri/platform/entity/analysis/ReportVersion.java
package com.agri.platform.entity.analysis;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReportVersion {
    private Long versionId;
    private Long reportId; // 关联的报告ID
    private String reportType; // 报告类型：MARKET_ANALYSIS, YIELD_PREDICTION
    private Integer versionNumber; // 版本号
    private String changeLog; // 变更日志
    private String createdBy; // 创建人
    private LocalDateTime createTime; // 创建时间
    private Boolean isCurrent; // 是否为当前版本
}