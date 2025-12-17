package com.agri.platform.controller;

import com.agri.platform.dto.StatsDTO;
import com.agri.platform.service.StatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api")
public class StatsController {

    @Resource
    private StatsService statsService;

    // 前端统计区加载时调用这个接口，返回 StatsDTO
    @GetMapping("/stats")
    public StatsDTO getStatsData() {
        return statsService.getStats();
    }
}