package com.agri.platform.controller.planning;

import com.agri.platform.entity.planning.PlantingPlan;
import com.agri.platform.service.planning.PlantingPlanService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/planting-plan") // 接口前缀
public class PlantingPlanController {
    @Autowired
    private PlantingPlanService plantingPlanService;

    // 新增种植计划（带产量预估）
    // 访问方式：POST http://localhost:8080/planting-plan/add
    @PostMapping("/add")
    public String addPlan(@RequestBody PlantingPlan plan) {
        // 示例：plan需包含farmerId、cropType、plantingArea、plantingTime
        boolean success = plantingPlanService.addPlanWithYield(plan);
        if (success) {
            return "种植计划新增成功！预估产量：" + plan.getExpectedYield() + "公斤";
        } else {
            return "种植计划新增失败！";
        }
    }

    // 按农场主ID查询计划
    // 访问方式：GET http://localhost:8080/planting-plan/farmer/1（1是农场主ID）
    @GetMapping("/farmer/{farmerId}")
    public List<PlantingPlan> getPlansByFarmer(@PathVariable String farmerId) {
        List<PlantingPlan> list = plantingPlanService.getPlansByFarmerId(farmerId);
        return list;
    }
}