// src/main/java/com/agri/platform/controller/analysis/YieldPredictionController.java
package com.agri.platform.controller.analysis;

import com.agri.platform.entity.analysis.YieldPrediction;
import com.agri.platform.entity.analysis.YieldPredictionModel;
import com.agri.platform.service.analysis.YieldPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/analysis/yield")
public class YieldPredictionController {

    @Autowired
    private YieldPredictionService yieldPredictionService;

    // 预测产量
    @PostMapping("/predict")
    public YieldPrediction predictYield(
            @RequestParam Long planId,
            @RequestParam Long modelId) {
        return yieldPredictionService.predictYield(planId, modelId);
    }

    // 批量预测
    @PostMapping("/batch-predict/{farmerId}")
    public List<YieldPrediction> batchPredict(@PathVariable Long farmerId) {
        return yieldPredictionService.batchPredictYield(farmerId);
    }

    // 获取种植计划的预测记录
    @GetMapping("/plan/{planId}")
    public List<YieldPrediction> getPredictionsByPlan(@PathVariable Long planId) {
        return yieldPredictionService.getPredictionsByPlanId(planId);
    }

    // 获取作物的预测模型
    @GetMapping("/models/{cropType}")
    public List<YieldPredictionModel> getModelsByCrop(@PathVariable String cropType) {
        return yieldPredictionService.getModelsByCropType(cropType);
    }

    // 添加预测模型
    @PostMapping("/model/add")
    public String addModel(@RequestBody YieldPredictionModel model) {
        boolean success = yieldPredictionService.addPredictionModel(model);
        return success ? "预测模型添加成功" : "预测模型添加失败";
    }
}