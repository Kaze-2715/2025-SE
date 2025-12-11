package com.agri.platform.util.analysis;

/**
 * 市场价格分析工具类
 */
public class MarketPriceUtil {

    /**
     * 根据价格计算趋势
     * @param price 当前价格
     * @return 趋势：上涨、下跌、平稳
     */
    public static String calculateTrend(double price) {
        if (price > 2.0) {
            return "上涨";
        } else if (price < 2.0) {
            return "下跌";
        } else {
            return "平稳";
        }
    }

    /**
     * 预测未来价格
     * @param currentPrice 当前价格
     * @param trendFactor 趋势因子
     * @param days 预测天数
     * @return 预测价格
     */
    public static double predictPrice(double currentPrice, double trendFactor, int days) {
        // 价格预测模型：预测价格 = 当前价格 * (趋势因子)^(天数/30)
        return currentPrice * Math.pow(trendFactor, days / 30.0);
    }
}