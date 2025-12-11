// src/main/java/com/agri/platform/service/analysis/MarketAnalysisService.java
package com.agri.platform.service.analysis;

import com.agri.platform.entity.analysis.MarketAnalysis;
import java.util.List;

public interface MarketAnalysisService {
    // 生成市场分析报告
    MarketAnalysis generateMarketAnalysis(String cropType);

    // 获取历史分析报告
    List<MarketAnalysis> getMarketAnalyses(String cropType);

    // 获取最新市场分析
    MarketAnalysis getLatestMarketAnalysis(String cropType);

    // 定时更新市场数据
    void scheduledUpdateMarketData();

}