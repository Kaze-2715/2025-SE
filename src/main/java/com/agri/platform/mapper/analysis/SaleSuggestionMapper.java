package com.agri.platform.mapper.analysis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import org.apache.ibatis.annotations.Param;

import com.agri.platform.entity.analysis.SaleSuggestion;

@Mapper
public interface SaleSuggestionMapper {
    @Select("WITH wp AS ( " +
        "  SELECT AVG(price) AS avg_wholesale " +
        "  FROM t_market_data " +
        "  WHERE crop_type = #{cropType} " +
        "    AND market_type = '批发市场' " +
        "    AND update_time >= DATE_SUB(NOW(), INTERVAL 7 DAY) " +
        "), " +
        "ep AS ( " +
        "  SELECT AVG(price) AS avg_ecom " +
        "  FROM t_ecommerce_data " +
        "  WHERE crop_type = #{cropType} " +
        "    AND crawl_time >= DATE_SUB(NOW(), INTERVAL 7 DAY) " +
        ") " +
        "SELECT " +
        "  CONCAT(DATE_FORMAT(DATE_ADD(NOW(), INTERVAL 15 DAY), '%m月%d日'), " +
        "         ' ~ ', " +
        "         DATE_FORMAT(DATE_ADD(NOW(), INTERVAL 40 DAY), '%m月%d日')) AS bestTime, " +
        "  CONCAT(ROUND(LEAST(wp.avg_wholesale, COALESCE(ep.avg_ecom, wp.avg_wholesale)) * 1.05, 2), " +
        "         ' ~ ', " +
        "         ROUND(GREATEST(wp.avg_wholesale, COALESCE(ep.avg_ecom, wp.avg_wholesale)) * 1.15, 2)) AS priceRange, " +
        "  IF(COALESCE(ep.avg_ecom, 0) > wp.avg_wholesale * 1.08, '电商', '零售') AS channel, " +
        "  CASE WHEN MONTH(NOW()) IN (6,9) THEN '季节性需求高峰，价格坚挺' " +
        "       ELSE '库存中性，随行就市' END AS reason " +
        "FROM wp, ep")
    SaleSuggestion getSuggestion(@Param("cropType") String cropType);
}
