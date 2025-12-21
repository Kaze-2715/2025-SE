package com.agri.platform.controller.report;

import org.springframework.web.bind.annotation.*;
import com.agri.platform.service.report.ReportService;
import com.agri.platform.DTO.report.PlantingReportCreateDTO;
import com.agri.platform.DTO.report.YieldReportCreateDTO;

import java.time.LocalDate;

/**
 * 报告生成相关接口
 */
@RestController
@RequestMapping("/api/report/generate")
public class ReportGenerateController {

    private final ReportService reportService;

    public ReportGenerateController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/planting")
    public Long generatePlanting(@RequestBody PlantingReportCreateDTO dto) {
        return reportService.generatePlantingRecordReport(dto);
    }

    @PostMapping("/resource")
    public Long generateResource(@RequestParam Long creatorId,
                                 @RequestParam Long farmId,
                                 @RequestParam String periodType,
                                 @RequestParam LocalDate start,
                                 @RequestParam LocalDate end,
                                 @RequestParam(required = false) String title) {
        return reportService.generateResourceUsageReport(creatorId, farmId, periodType, start, end, title);
    }

    @PostMapping("/yield")
    public Long generateYield(@RequestBody YieldReportCreateDTO dto) {
        return reportService.generateYieldReport(dto);
    }
}
