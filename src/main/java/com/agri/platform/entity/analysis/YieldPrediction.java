// src/main/java/com/agri/platform/entity/analysis/YieldPrediction.java
package com.agri.platform.entity.analysis;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class YieldPrediction {
    private Long predictionId;
    private Long planId; // 关联种植计划ID
    private Long modelId; // 使用的预测模型ID
    private String cropType;
    private BigDecimal predictedYield; // 预测产量(公斤)
    private BigDecimal confidence; // 置信度(0-1)
    private LocalDate predictionDate; // 预测日期
    private LocalDate harvestDate; // 预计收获日期
    private String factors; // 影响因素
    private LocalDateTime createTime;
}