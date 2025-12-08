// src/main/java/com/agri/platform/controller/analysis/ReportVersionController.java
package com.agri.platform.controller.analysis;

import com.agri.platform.entity.analysis.ReportVersion;
import com.agri.platform.service.analysis.ReportVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/analysis/report/version")
public class ReportVersionController {

    @Autowired
    private ReportVersionService versionService;

    @PostMapping("/create")
    public ReportVersion createVersion(
            @RequestParam Long reportId,
            @RequestParam String reportType,
            @RequestParam String changeLog,
            @RequestParam String operator) {
        return versionService.createNewVersion(reportId, reportType, changeLog, operator);
    }

    @GetMapping("/list")
    public List<ReportVersion> getVersions(
            @RequestParam Long reportId,
            @RequestParam String reportType) {
        return versionService.getReportVersions(reportId, reportType);
    }

    @GetMapping("/current")
    public ReportVersion getCurrentVersion(
            @RequestParam Long reportId,
            @RequestParam String reportType) {
        return versionService.getCurrentVersion(reportId, reportType);
    }

    @PostMapping("/switch")
    public boolean switchVersion(
            @RequestParam Long versionId,
            @RequestParam String operator) {
        return versionService.switchVersion(versionId, operator);
    }
}