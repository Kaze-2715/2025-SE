package com.agri.platform.service.analysis;

import com.agri.platform.entity.analysis.MarketAnalysis;
import com.agri.platform.mapper.analysis.MarketAnalysisMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MarketAnalysisServiceTest {

    @Mock
    private MarketAnalysisMapper marketAnalysisMapper;

    @InjectMocks
    private MarketAnalysisServiceImpl marketAnalysisService;

    // 测试获取市场分析列表
    @Test
    void testGetMarketAnalyses() {
        // 1. 模拟依赖返回
        String cropType = "小麦";
        List<MarketAnalysis> mockList = new ArrayList<>();
        MarketAnalysis analysis = new MarketAnalysis();
        analysis.setCropType("小麦");
        analysis.setCurrentPrice(new BigDecimal("2.3"));
        mockList.add(analysis);

        when(marketAnalysisMapper.selectByCropType(cropType)).thenReturn(mockList);

        // 2. 执行测试方法
        List<MarketAnalysis> result = marketAnalysisService.getMarketAnalyses(cropType);

        // 3. 断言结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("小麦", result.get(0).getCropType());
        assertEquals(new BigDecimal("2.3"), result.get(0).getCurrentPrice());
    }
}