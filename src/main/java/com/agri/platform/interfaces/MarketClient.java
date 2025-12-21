package com.agri.platform.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 市场行情与销售服务远程调用客户端
 * 对应服务名: smartagri-market
 */
@FeignClient(name = "smartagri-market", path = "/market")
public interface MarketClient {

    /**
     * 获取指定农作物的近期市场价格走势
     * 用于：市场分析报告
     */
    @GetMapping("/price/trend")
    List<Map<String, Object>> getPriceTrend(@RequestParam("cropName") String cropName);

    /**
     * 获取销售订单汇总数据
     * 用于：计算销售收入、平均售价
     */
    @GetMapping("/order/summary")
    Map<String, Object> getSalesSummary(@RequestParam("planId") Long planId);
}
