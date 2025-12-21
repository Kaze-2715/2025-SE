package com.agri.platform.controller.report;

import com.agri.platform.entity.report.ExportLog;
import org.springframework.web.bind.annotation.*;
import com.agri.platform.service.report.ReportExportService;
import com.agri.platform.enums.ExportFormatEnum;

/**
 * 报告导出接口
 */
@RestController
@RequestMapping("/api/report/export")
public class ReportExportController {

    private final ReportExportService reportExportService;

    public ReportExportController(ReportExportService reportExportService) {
        this.reportExportService = reportExportService;
    }

    @PostMapping
    public String export(@RequestParam Long reportId,
                         @RequestParam Long exporterId,
                         @RequestParam ExportFormatEnum format) {
        return reportExportService.export(reportId, exporterId, format);
    }
}
