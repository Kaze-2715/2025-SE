// src/main/java/com/agri/platform/entity/analysis/MarketAnalysis.java
package com.agri.platform.entity.analysis;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MarketAnalysis {
    private Long analysisId;
    private String cropType;
    private LocalDate analysisDate; // 分析日期
    private BigDecimal currentPrice; // 当前市场价(元/公斤)
    private BigDecimal predictedPrice; // 预测价格(元/公斤)
    private String priceTrend; // 价格趋势：上涨、下跌、平稳
    private String marketDemand; // 市场需求：高、中、低
    private String mainRegions; // 主要市场区域
    private String influencingFactors; // 影响因素
    private LocalDateTime createTime;
}