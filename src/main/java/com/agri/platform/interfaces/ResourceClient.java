package com.agri.platform.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 资源与资产服务远程调用客户端
 * 对应服务名: smartagri-resource
 */
@FeignClient(name = "smartagri-resource", path = "/resource")
public interface ResourceClient {

    /**
     * 根据ID获取地块/土地详细信息
     * 用于：报告头部显示地块位置、面积、土壤类型
     */
    @GetMapping("/land/{id}")
    Map<String, Object> getLandInfo(@PathVariable("id") Long landId);

    /**
     * 获取指定种植计划的农资消耗统计
     * (肥料、农药、种子等的用量和成本)
     * 用于：成本核算报告
     */
    @GetMapping("/material/usage/stats")
    List<Map<String, Object>> getMaterialUsageStats(@RequestParam("planId") Long planId);

    /**
     * 获取农机使用记录
     */
    @GetMapping("/machine/usage/list")
    List<Map<String, Object>> getMachineUsageList(@RequestParam("planId") Long planId);
}
