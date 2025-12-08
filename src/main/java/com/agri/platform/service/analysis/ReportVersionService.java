// src/main/java/com/agri/platform/service/analysis/ReportVersionService.java
package com.agri.platform.service.analysis;

import com.agri.platform.entity.analysis.ReportVersion;
import java.util.List;

public interface ReportVersionService {
    // 创建新版本
    ReportVersion createNewVersion(Long reportId, String reportType, String changeLog, String operator);

    // 获取报告的所有版本
    List<ReportVersion> getReportVersions(Long reportId, String reportType);

    // 获取当前版本
    ReportVersion getCurrentVersion(Long reportId, String reportType);

    // 切换版本
    boolean switchVersion(Long versionId, String operator);
}