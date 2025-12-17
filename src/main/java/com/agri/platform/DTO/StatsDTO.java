package com.agri.platform.dto;

import lombok.Data;

/**
 * 统计数据DTO（后端→前端）
 */
@Data
public class StatsDTO {

    // 合作农户数
    private Integer farmerCount;

    // 覆盖农场数
    private Integer farmCount;

    // 合作省份数
    private Integer provinceCount;

    // 产量提升百分比
    private Integer yieldIncrease;
}