package com.agri.platform.util.report;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.agri.platform.mapper.report.ReportMapper;
import com.agri.platform.entity.report.Report;
import com.agri.platform.enums.ExportFormatEnum;
import com.agri.platform.enums.ReportTypeEnum;

/**
 * 报告工具/门面类
 * - 提供文件命名、类型解析等通用逻辑
 */
@Component
public class ReportApplication {

    private final ReportMapper reportMapper;

    public ReportApplication(ReportMapper reportMapper) {
        this.reportMapper = reportMapper;
    }

    public String buildExportFileName(Long reportId, ExportFormatEnum format) {
        Report r = reportMapper.selectById(reportId);
        String typePart = r == null ? "UNKNOWN" : r.getType();
        String creator = r == null ? "unknown" : String.valueOf(r.getCreatorId());
        String timePart = LocalDateTime.now().toString().replace(':', '-');
        return typePart + "-" + timePart + "-" + creator + "." + (format == ExportFormatEnum.PDF ? "pdf" : "xlsx");
    }

    public ReportTypeEnum parseType(String s) {
        return ReportTypeEnum.valueOf(s);
    }
}
