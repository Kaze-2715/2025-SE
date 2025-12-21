package com.agri.platform.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 种植服务远程调用客户端
 * 对应服务名: **###**artagri-planting (请修改为您实际的服务名)
 */
@FeignClient(name = "smartagri-planting", path = "/planting")
public interface PlantingClient {

    /**
     * 根据ID获取种植计划详情
     * 用于：报告中展示农作物名称、种植面积、开始时间等
     */
    @GetMapping("/plan/{id}")
    Map<String, Object> getPlanInfo(@PathVariable("id") Long planId);

    /**
     * 获取某次种植计划的所有农事操作记录
     * 用于：生成种植过程的时间轴
     */
    @GetMapping("/operation/list")
    List<Map<String, Object>> getOperationList(@RequestParam("planId") Long planId);

    /**
     * 获取实际收获/产量数据
     * 用于：与预估产量对比，生成投入产出分析
     */
    @GetMapping("/harvest/record")
    Map<String, Object> getHarvestRecord(@RequestParam("planId") Long planId);

    // 新增：获取种植计划概要（用于报告头部/摘要）
    @GetMapping("/plan/{id}/summary")
    Map<String, Object> getPlanSummary(@PathVariable("id") Long planId);

    // 新增：获取指定地块的农事作业列表（用于分地块作业明细）
    @GetMapping("/field/{fieldId}/operations")
    List<Map<String, Object>> getFieldOperations(@PathVariable("fieldId") Long fieldId);
}
