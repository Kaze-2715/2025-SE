package com.agri.platform.service.report;

import com.agri.platform.enums.ExportFormatEnum;



/**
 * 报告导出接口（功能-9）
 */
public interface ReportExportService {

    /**
     * 导出报告为指定格式，返回生成的文件名或路径
     * @param reportId 报告ID
     * @param exporterId 导出人用户ID
     * @param format 导出格式（PDF/XLSX/CSV）
     * @return 文件名（或存储路径）
     */
    String export(Long reportId, Long exporterId, ExportFormatEnum format);
}
