package com.agri.platform.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 环境监控服务远程调用客户端
 * 对应服务名: smartagri-monitor
 */
@FeignClient(name = "smartagri-monitor", path = "/monitor")
public interface MonitorClient {

    /**
     * 获取指定地块、指定时间范围内的环境数据聚合值
     * (例如：平均温度、累计降雨量、光照时长)
     * 
     * @param landId 地块ID
     * @param startTime 开始时间 (yyyy-MM-dd HH:mm:ss)
     * @param endTime 结束时间
     */
    @GetMapping("/sensor/history/aggregation")
    Map<String, Object> getEnvironmentAggregation(
            @RequestParam("landId") Long landId,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime
    );

    /**
     * 获取详细的历史曲线数据
     * 用于：报告中绘制温湿度变化曲线图
     */
    @GetMapping("/sensor/history/list")
    List<Map<String, Object>> getHistoryData(
            @RequestParam("landId") Long landId,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime
    );
}
