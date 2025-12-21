package com.agri.platform.controller.report;

import java.util.List;

import com.agri.platform.DTO.report.ReportShareDTO;
import com.agri.platform.enums.SharePermissionEnum;
import org.springframework.web.bind.annotation.*;
import com.agri.platform.service.report.ReportShareService;

/**
 * 报告分享接口
 */
@RestController
@RequestMapping("/api/report/share")
public class ReportShareController {

    private final ReportShareService reportShareService;

    public ReportShareController(ReportShareService reportShareService) {
        this.reportShareService = reportShareService;
    }

    @PostMapping("/do")
    public void share(@RequestBody ReportShareDTO dto) {
        reportShareService.share(dto);
    }

    @PostMapping("/cancel")
    public void cancel(@RequestParam Long reportId, @RequestParam Long toUserId, @RequestParam Long operatorId) {
        reportShareService.cancelShare(reportId, toUserId, operatorId);
    }

    @GetMapping("/targets/{reportId}")
    public List<Long> targets(@PathVariable("reportId") Long reportId) {
        return reportShareService.listShareTargets(reportId);
    }

    @PostMapping("/permission")
    public void changePermission(@RequestParam Long reportId,
                                 @RequestParam Long toUserId,
                                 @RequestParam SharePermissionEnum permission,
                                 @RequestParam Long operatorId) {
        reportShareService.changePermission(reportId, toUserId, permission, operatorId);
    }
}
