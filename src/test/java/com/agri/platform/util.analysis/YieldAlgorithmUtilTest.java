package com.agri.platform.util.analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class YieldAlgorithmUtilTest {

    // 测试线性回归产量预测算法
    @Test
    void testLinearRegressionPrediction() {
        // 1. 准备测试数据
        BigDecimal baseYield = new BigDecimal("500");
        int growthDays = 100;
        double temperatureFactor = 1.2;
        double rainfallFactor = 0.9;

        // 2. 执行测试方法
        BigDecimal result = YieldAlgorithmUtil.linearRegressionPrediction(baseYield, growthDays, temperatureFactor, rainfallFactor);

        // 3. 断言结果
        assertNotNull(result);
        // 线性回归模型：产量 = 基准产量 * (生长天数/100) * 温度因子 * 降雨因子
        BigDecimal expected = baseYield.multiply(new BigDecimal(growthDays/100.0))
                .multiply(new BigDecimal(temperatureFactor))
                .multiply(new BigDecimal(rainfallFactor));
        assertEquals(expected, result);
    }

    // 测试产量调整算法
    @Test
    void testAdjustYield() {
        // 1. 准备测试数据
        BigDecimal predictedYield = new BigDecimal("600");
        double riskFactor = 0.8;

        // 2. 执行测试方法
        BigDecimal result = YieldAlgorithmUtil.adjustYield(predictedYield, riskFactor);

        // 3. 断言结果
        assertNotNull(result);
        // 产量调整：调整后产量 = 预测产量 * 风险因子
        BigDecimal expected = predictedYield.multiply(new BigDecimal(riskFactor));
        assertEquals(expected, result);
    }
}
