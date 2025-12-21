package com.agri.platform.mapper.report;

import com.agri.platform.entity.report.ExportLog;


/**
 * 导出日志持久层
 */
public interface ExportLogMapper {
    void insert(ExportLog log);
}
