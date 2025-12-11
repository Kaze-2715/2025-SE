// src/main/java/com/agri/platform/controller/analysis/MarketAnalysisController.java
package com.agri.platform.controller.analysis;

import com.agri.platform.entity.analysis.MarketAnalysis;
import com.agri.platform.service.analysis.MarketAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/analysis/market")
public class MarketAnalysisController {

    @Autowired
    private MarketAnalysisService marketAnalysisService;

    // 生成市场分析报告
    @PostMapping("/generate")
    public MarketAnalysis generateAnalysis(@RequestParam String cropType) {
        return marketAnalysisService.generateMarketAnalysis(cropType);
    }

    // 获取历史分析报告
    @GetMapping("/history/{cropType}")
    public List<MarketAnalysis> getMarketHistory(@PathVariable String cropType) {
        return marketAnalysisService.getMarketAnalyses(cropType);
    }

    // 获取最新市场分析
    @GetMapping("/latest/{cropType}")
    public MarketAnalysis getLatestAnalysis(@PathVariable String cropType) {
        return marketAnalysisService.getLatestMarketAnalysis(cropType);
    }
}