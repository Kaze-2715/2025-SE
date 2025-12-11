// src/main/java/com/agri/platform/entity/analysis/YieldPredictionModel.java
package com.agri.platform.entity.analysis;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class YieldPredictionModel {
    private Long modelId;
    private String cropType;
    private String modelName;
    private String description;
    private String algorithmType; // 算法类型：如线性回归、神经网络等
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Double accuracy; // 模型准确率
    private String status; // 状态：启用、禁用
}