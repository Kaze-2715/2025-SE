package com.agri.platform.util.analysis;

import java.math.BigDecimal;

/**
 * 产量预测算法工具类
 */
public class YieldAlgorithmUtil {

    /**
     * 线性回归产量预测
     * @param baseYield 基准产量
     * @param growthDays 生长天数
     * @param temperatureFactor 温度因子
     * @param rainfallFactor 降雨因子
     * @return 预测产量
     */
    public static BigDecimal linearRegressionPrediction(BigDecimal baseYield, int growthDays,
                                                        double temperatureFactor, double rainfallFactor) {
        // 线性回归模型：产量 = 基准产量 * (生长天数/100) * 温度因子 * 降雨因子
        return baseYield.multiply(new BigDecimal(growthDays/100.0))
                .multiply(new BigDecimal(temperatureFactor))
                .multiply(new BigDecimal(rainfallFactor));
    }

    /**
     * 产量调整算法
     * @param predictedYield 预测产量
     * @param riskFactor 风险因子
     * @return 调整后产量
     */
    public static BigDecimal adjustYield(BigDecimal predictedYield, double riskFactor) {
        // 产量调整：调整后产量 = 预测产量 * 风险因子
        return predictedYield.multiply(new BigDecimal(riskFactor));
    }
}