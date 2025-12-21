package com.agri.platform.controller.report;

import java.util.List;

import com.agri.platform.DTO.report.ReportQueryDTO;
import com.agri.platform.VO.report.ReportDetailVO;
import com.agri.platform.VO.report.ReportListVO;
import com.agri.platform.VO.report.VersionDiffVO;
import org.springframework.web.bind.annotation.*;
import com.agri.platform.service.report.ReportService;

/**
 * 报告管理接口：查询、详情、编辑、版本
 */
@RestController
@RequestMapping("/api/report/manage")
public class ReportManageController {

    private final ReportService reportService;

    public ReportManageController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/query")
    public List<ReportListVO> query(@RequestBody ReportQueryDTO query) {
        return reportService.queryReports(query);
    }

    @GetMapping("/detail/{id}")
    public ReportDetailVO detail(@PathVariable("id") Long id, @RequestParam Long currentUserId) {
        return reportService.getReportDetail(id, currentUserId);
    }

    @PostMapping("/edit/{id}")
    public Integer edit(@PathVariable("id") Long id,
                        @RequestParam Long editorId,
                        @RequestParam String contentJson,
                        @RequestParam String changeSummary) {
        return reportService.editReportAndNewVersion(id, editorId, contentJson, changeSummary);
    }

    @GetMapping("/diff")
    public VersionDiffVO diff(@RequestParam Long reportId,
                              @RequestParam Integer fromVersion,
                              @RequestParam Integer toVersion) {
        return reportService.diffBetweenVersions(reportId, fromVersion, toVersion);
    }
}
