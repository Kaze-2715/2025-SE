// src/main/java/com/agri/platform/service/analysis/ReportVersionServiceImpl.java
package com.agri.platform.service.analysis;

import com.agri.platform.entity.analysis.ReportVersion;
import com.agri.platform.mapper.analysis.ReportVerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportVersionServiceImpl implements ReportVersionService {

    @Autowired
    private ReportVerMapper versionMapper;

    @Override
    @Transactional
    public ReportVersion createNewVersion(Long reportId, String reportType, String changeLog, String operator) {
        // 获取当前最新版本号
        ReportVersion current = versionMapper.selectCurrentVersion(reportId, reportType);
        int newVersionNum = current != null ? current.getVersionNumber() + 1 : 1;

        // 将当前版本标记为非当前
        if (current != null) {
            versionMapper.updateCurrentStatus(reportId, reportType, false);
        }

        // 创建新版本
        ReportVersion newVersion = new ReportVersion();
        newVersion.setReportId(reportId);
        newVersion.setReportType(reportType);
        newVersion.setVersionNumber(newVersionNum);
        newVersion.setChangeLog(changeLog);
        newVersion.setCreatedBy(operator);
        newVersion.setCreateTime(LocalDateTime.now());
        newVersion.setIsCurrent(true);

        versionMapper.insert(newVersion);
        return newVersion;
    }

    @Override
    public List<ReportVersion> getReportVersions(Long reportId, String reportType) {
        return versionMapper.selectByReport(reportId, reportType);
    }

    @Override
    public ReportVersion getCurrentVersion(Long reportId, String reportType) {
        return versionMapper.selectCurrentVersion(reportId, reportType);
    }

    @Override
    @Transactional
    public boolean switchVersion(Long versionId, String operator) {
        ReportVersion targetVersion = versionMapper.selectById(versionId);
        if (targetVersion == null) {
            return false;
        }

        // 将所有版本标记为非当前
        versionMapper.updateCurrentStatus(
                targetVersion.getReportId(),
                targetVersion.getReportType(),
                false
        );

        // 将目标版本标记为当前
        targetVersion.setIsCurrent(true);
        versionMapper.update(targetVersion);
        return true;
    }
}