package com.agri.platform.mapper.report;

import com.agri.platform.entity.report.ExportLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 导出日志 Mapper
 * 对应数据库表: export_log
 */
@Mapper
public interface ReportExportLogMapper {

    /**
     * 记录导出日志
     * 对应功能：9 报告导出功能 (系统记录导出日志)
     */
    int insert(ExportLog exportLog);

    /**
     * 查询某人的导出记录 (可选)
     */
    List<ExportLog> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 查询某份报告的导出记录 (可选)
     */
    List<ExportLog> selectByReportId(@Param("reportId") Long reportId);
}
