// src/main/java/com/agri/platform/mapper/analysis/ReportVersionMapper.java
package com.agri.platform.mapper.analysis;

import com.agri.platform.entity.analysis.ReportVersion;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ReportVersionMapper {
    ReportVersion selectById(Long versionId);
    List<ReportVersion> selectByReport(Long reportId, String reportType);
    ReportVersion selectCurrentVersion(Long reportId, String reportType);
    int insert(ReportVersion version);
    int update(ReportVersion version);
    int updateCurrentStatus(Long reportId, String reportType, Boolean isCurrent);
}