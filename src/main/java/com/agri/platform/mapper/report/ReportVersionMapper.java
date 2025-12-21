package com.agri.platform.mapper.report;

import com.agri.platform.entity.report.ReportVersion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 报告历史版本 Mapper
 * 对应数据库表: report_version
 */
@Mapper
public interface ReportVersionMapper {

    /**
     * 保存新版本快照
     * 对应功能：7 报告编辑与版本管理 (系统自动生成新版本)
     */
    int insert(ReportVersion version);

    /**
     * 获取某份报告的所有历史版本列表 (仅摘要信息)
     * 对应功能：7 查看版本差异
     */
    List<ReportVersion> selectByReportId(@Param("reportId") Long reportId);

    /**
     * 获取指定报告的指定版本详情
     * 对应功能：7 查看特定版本详情
     */
    ReportVersion selectByReportIdAndVersion(@Param("reportId") Long reportId, 
            @Param("versionNumber") String versionNumber);

    ReportVersion selectByReportIdAndVersion(Long reportId, Integer versionNo);
}
