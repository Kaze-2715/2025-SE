package com.agri.platform.mapper.analysis;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.agri.platform.controller.analysis.PriceFluctuationController.PriceTrendResp;

@Mapper
public interface MarketDataMapper {

    /** 最新一条批发价（今日价） */
    @Select("SELECT price FROM t_market_data " +
            "WHERE crop_type = #{crop} " +
            "ORDER BY update_time DESC LIMIT 1")
    BigDecimal selectLatestWholesale(@Param("crop") String crop);

    /** 最近 N 天批发价（趋势折线） */
    @Select("SELECT update_time AS date, price " +
            "FROM t_market_data " +
            "WHERE crop_type = #{crop} " +
            "  AND update_time >= DATE_SUB(CURDATE(), INTERVAL #{days} DAY) " +
            "ORDER BY update_time")
    List<PriceTrendResp> selectTrendWholesale(@Param("crop") String crop,
            @Param("days") int days);

    /** 最近 30 天均价（报警用） */
    @Select("SELECT AVG(price) FROM t_market_data " +
            "WHERE crop_type = #{crop} " +
            "  AND update_time >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)")
    BigDecimal avg30Wholesale(@Param("crop") String crop);

    /** 今日价（报警用） */
    @Select("SELECT price FROM t_market_data " +
            "WHERE crop_type = #{crop} " +
            "  AND update_time = CURDATE() " +
            "ORDER BY update_time DESC LIMIT 1")
    BigDecimal todayWholesale(@Param("crop") String crop);
}
