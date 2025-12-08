// src/main/java/com/agri/platform/service/analysis/YieldPredictionServiceImpl.java
package com.agri.platform.service.analysis;

import com.agri.platform.entity.analysis.YieldPrediction;
import com.agri.platform.entity.analysis.YieldPredictionModel;
import com.agri.platform.entity.planning.PlantingPlan;
import com.agri.platform.mapper.analysis.YieldPredictionMapper;
import com.agri.platform.mapper.analysis.YieldPredictionModelMapper;
import com.agri.platform.mapper.planning.PlantingPlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class YieldPredictionServiceImpl implements YieldPredictionService {

    @Autowired
    private YieldPredictionMapper yieldPredictionMapper;

    @Autowired
    private YieldPredictionModelMapper modelMapper;

    @Autowired
    private PlantingPlanMapper plantingPlanMapper;

    @Override
    public YieldPrediction predictYield(Long planId, Long modelId) {
        // 获取种植计划
        PlantingPlan plan = plantingPlanMapper.selectById(planId);
        if (plan == null) {
            throw new IllegalArgumentException("种植计划不存在");
        }

        // 获取预测模型
        YieldPredictionModel model = modelMapper.selectById(modelId);
        if (model == null) {
            throw new IllegalArgumentException("预测模型不存在");
        }

        // 基于模型进行产量预测（实际项目中这里会调用真实的预测算法）
        YieldPrediction prediction = new YieldPrediction();
        prediction.setPlanId(planId);
        prediction.setModelId(modelId);
        prediction.setCropType(plan.getCropType());
        prediction.setPredictionDate(LocalDate.now());

        // 模拟预测算法
        BigDecimal baseYield = plan.getExpectedYield();
        // 根据不同模型添加不同的修正因子
        double factor = getPredictionFactor(model.getAlgorithmType());
        BigDecimal predictedYield = baseYield.multiply(new BigDecimal(1 + factor));
        prediction.setPredictedYield(predictedYield);

        // 设置置信度（0-1）
        prediction.setConfidence(new BigDecimal(Math.random() * 0.3 + 0.7)); // 70%-100%

        // 预计收获日期（种植时间+作物生长周期）
        prediction.setHarvestDate(plan.getPlantingTime().plusDays(getGrowthDays(plan.getCropType())));

        // 影响因素
        prediction.setFactors("天气:良好, 土壤:中等, 管理:规范");
        prediction.setCreateTime(LocalDateTime.now());

        // 保存预测结果
        yieldPredictionMapper.insert(prediction);

        return prediction;
    }

    @Override
    public List<YieldPrediction> batchPredictYield(Long farmerId) {
        // 实际项目中应该查询该农场主的所有种植计划并进行批量预测
        return new ArrayList<>();
    }

    @Override
    public List<YieldPrediction> getPredictionsByPlanId(Long planId) {
        return yieldPredictionMapper.selectByPlanId(planId);
    }

    @Override
    public List<YieldPredictionModel> getModelsByCropType(String cropType) {
        return modelMapper.selectByCropType(cropType);
    }

    @Override
    public boolean addPredictionModel(YieldPredictionModel model) {
        model.setCreateTime(LocalDateTime.now());
        model.setUpdateTime(LocalDateTime.now());
        return modelMapper.insert(model) > 0;
    }

    // 获取不同作物的生长周期（天）
    private int getGrowthDays(String cropType) {
        switch (cropType) {
            case "小麦": return 210;
            case "水稻": return 150;
            case "玉米": return 120;
            default: return 180;
        }
    }

    // 根据算法类型获取预测因子
    private double getPredictionFactor(String algorithmType) {
        Random random = new Random();
        switch (algorithmType) {
            case "线性回归": return (random.nextDouble() - 0.5) * 0.1; // -5% 到 +5%
            case "神经网络": return (random.nextDouble() - 0.5) * 0.08; // -4% 到 +4%
            case "决策树": return (random.nextDouble() - 0.5) * 0.12; // -6% 到 +6%
            default: return (random.nextDouble() - 0.5) * 0.15; // -7.5% 到 +7.5%
        }
    }
}