package com.agri.platform.util.analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MarketPriceUtilTest {

    // 测试价格趋势计算
    @Test
    void testCalculateTrend() {
        // 测试上涨趋势
        assertEquals("上涨", MarketPriceUtil.calculateTrend(3.0));

        // 测试下跌趋势
        assertEquals("下跌", MarketPriceUtil.calculateTrend(1.0));

        // 测试平稳趋势
        assertEquals("平稳", MarketPriceUtil.calculateTrend(2.0));
    }

    // 测试价格预测
    @Test
    void testPredictPrice() {
        // 1. 准备测试数据
        double currentPrice = 2.5;
        double trendFactor = 1.1;
        int days = 30;

        // 2. 执行测试方法
        double result = MarketPriceUtil.predictPrice(currentPrice, trendFactor, days);

        // 3. 断言结果
        // 价格预测模型：预测价格 = 当前价格 * (趋势因子)^(天数/30)
        double expected = currentPrice * Math.pow(trendFactor, days/30.0);
        assertEquals(expected, result, 0.001); // 使用容差比较浮点数
    }
}
