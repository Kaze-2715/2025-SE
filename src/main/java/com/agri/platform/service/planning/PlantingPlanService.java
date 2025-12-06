package com.agri.platform.service.planning;

import com.agri.platform.entity.planning.PlantingPlan;
import com.agri.platform.mapper.planning.PlantingPlanMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class PlantingPlanService {
    @Autowired
    private PlantingPlanMapper plantingPlanMapper; // 现在能正常注入了

    // 新增种植计划，并自动计算预估产量
    public boolean addPlanWithYield(PlantingPlan plan) {
        // 核心：产量计算逻辑（按作物类型+种植面积估算）
        BigDecimal expectedYield = calculateYield(plan.getCropType(), plan.getPlantingArea());
        plan.setExpectedYield(expectedYield);
        plan.setYieldBasis("基于作物默认单产模型（v1.0）：小麦500公斤/亩、水稻600公斤/亩、其他400公斤/亩");

        // 保存到数据库
        return plantingPlanMapper.insert(plan) > 0;
    }

    // 产量计算工具方法
    private BigDecimal calculateYield(String cropType, BigDecimal plantingArea) {
        BigDecimal perUnitYield; // 单位面积产量（公斤/亩）
        switch (cropType) {
            case "小麦":
                perUnitYield = new BigDecimal("500");
                break;
            case "水稻":
                perUnitYield = new BigDecimal("600");
                break;
            case "玉米":
                perUnitYield = new BigDecimal("550");
                break;
            default:
                perUnitYield = new BigDecimal("400"); // 其他作物默认值
        }
        return plantingArea.multiply(perUnitYield); // 总产量 = 面积 * 单产
    }

    // 按农场主ID查询种植计划
    public java.util.List<PlantingPlan> getPlansByFarmerId(Long farmerId) {
        return plantingPlanMapper.selectByFarmerId(farmerId);
    }
}