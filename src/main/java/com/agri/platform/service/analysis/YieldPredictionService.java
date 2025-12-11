// src/main/java/com/agri/platform/service/analysis/YieldPredictionService.java
package com.agri.platform.service.analysis;

import com.agri.platform.entity.analysis.YieldPrediction;
import com.agri.platform.entity.analysis.YieldPredictionModel;
import java.util.List;

public interface YieldPredictionService {
    // 预测产量
    YieldPrediction predictYield(Long planId, Long modelId);

    // 批量预测
    List<YieldPrediction> batchPredictYield(Long farmerId);

    // 获取历史预测记录
    List<YieldPrediction> getPredictionsByPlanId(Long planId);

    // 获取预测模型
    List<YieldPredictionModel> getModelsByCropType(String cropType);

    // 添加预测模型
    boolean addPredictionModel(YieldPredictionModel model);
}