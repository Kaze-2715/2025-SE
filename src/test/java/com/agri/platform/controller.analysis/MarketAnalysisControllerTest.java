package com.agri.platform.controller.analysis;

import com.agri.platform.entity.analysis.MarketAnalysis;
import com.agri.platform.service.analysis.MarketAnalysisService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MarketAnalysisControllerTest {

    @Mock
    private MarketAnalysisService marketAnalysisService;

    @InjectMocks
    private MarketAnalysisController marketAnalysisController;

    // 测试生成市场分析报告接口
    @Test
    void testGenerateAnalysis() {
        // 1. 模拟依赖返回
        String cropType = "小麦";
        MarketAnalysis analysis = new MarketAnalysis();
        analysis.setCropType("小麦");
        analysis.setCurrentPrice(new BigDecimal(2.5));
        analysis.setPriceTrend("上涨");

        when(marketAnalysisService.generateMarketAnalysis(cropType)).thenReturn(analysis);

        // 2. 执行测试方法
        MarketAnalysis response = marketAnalysisController.generateAnalysis(cropType);

        // 3. 断言结果
        assertEquals("小麦", response.getCropType());
        assertEquals(new BigDecimal(2.5), response.getCurrentPrice());
        assertEquals("上涨", response.getPriceTrend());
    }

    // 测试获取历史分析报告接口
    @Test
    void testGetMarketHistory() {
        // 1. 模拟依赖返回
        String cropType = "小麦";
        List<MarketAnalysis> analysisList = new ArrayList<>();
        MarketAnalysis analysis = new MarketAnalysis();
        analysis.setCropType("小麦");
        analysis.setCurrentPrice(new BigDecimal(2.5));
        analysis.setPriceTrend("上涨");
        analysisList.add(analysis);

        when(marketAnalysisService.getMarketAnalyses(cropType)).thenReturn(analysisList);

        // 2. 执行测试方法
        List<MarketAnalysis> response = marketAnalysisController.getMarketHistory(cropType);

        // 3. 断言结果
        assertEquals(1, response.size());
        assertEquals("小麦", response.get(0).getCropType());
        assertEquals(new BigDecimal(2.5), response.get(0).getCurrentPrice());
        assertEquals("上涨", response.get(0).getPriceTrend());
    }

    // 测试获取最新市场分析接口
    @Test
    void testGetLatestAnalysis() {
        // 1. 模拟依赖返回
        String cropType = "小麦";
        MarketAnalysis analysis = new MarketAnalysis();
        analysis.setCropType("小麦");
        analysis.setCurrentPrice(new BigDecimal(2.5));
        analysis.setPriceTrend("上涨");

        when(marketAnalysisService.getLatestMarketAnalysis(cropType)).thenReturn(analysis);

        // 2. 执行测试方法
        MarketAnalysis response = marketAnalysisController.getLatestAnalysis(cropType);

        // 3. 断言结果
        assertEquals("小麦", response.getCropType());
        assertEquals(new BigDecimal(2.5), response.getCurrentPrice());
        assertEquals("上涨", response.getPriceTrend());
    }
}