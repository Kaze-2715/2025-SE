// src/main/java/com/agri/platform/mapper/analysis/MarketAnalysisMapper.java
package com.agri.platform.mapper.analysis;

import com.agri.platform.entity.analysis.MarketAnalysis;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface MarketAnalysisMapper {
    /* ==================== 查询 ==================== */
    @Select("SELECT analysis_id, crop_type, analysis_date, current_price, predicted_price, " +
            "price_trend, market_demand, main_regions, influencing_factors, create_time " +
            "FROM t_market_analysis " +
            "WHERE analysis_id = #{analysisId}")
    @Results(id = "marketAnalysisMap", value = {
            @Result(column = "analysis_id", property = "analysisId", jdbcType = JdbcType.BIGINT),
            @Result(column = "crop_type", property = "cropType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "analysis_date", property = "analysisDate", jdbcType = JdbcType.DATE),
            @Result(column = "current_price", property = "currentPrice", jdbcType = JdbcType.DECIMAL),
            @Result(column = "predicted_price", property = "predictedPrice", jdbcType = JdbcType.DECIMAL),
            @Result(column = "price_trend", property = "priceTrend", jdbcType = JdbcType.VARCHAR),
            @Result(column = "market_demand", property = "marketDemand", jdbcType = JdbcType.VARCHAR),
            @Result(column = "main_regions", property = "mainRegions", jdbcType = JdbcType.VARCHAR),
            @Result(column = "influencing_factors", property = "influencingFactors", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP)
    })
    MarketAnalysis selectById(Long analysisId);

    @ResultMap("marketAnalysisMap")
    @Select("SELECT analysis_id, crop_type, analysis_date, current_price, predicted_price, " +
            "price_trend, market_demand, main_regions, influencing_factors, create_time " +
            "FROM t_market_analysis " +
            "WHERE crop_type = #{cropType}")
    List<MarketAnalysis> selectByCropType(String cropType);

    @ResultMap("marketAnalysisMap")
    @Select("SELECT analysis_id, crop_type, analysis_date, current_price, predicted_price, " +
            "price_trend, market_demand, main_regions, influencing_factors, create_time " +
            "FROM t_market_analysis " +
            "WHERE crop_type = #{cropType} " +
            "ORDER BY analysis_date DESC " +
            "LIMIT 20")
    List<MarketAnalysis> selectLatestByCropType(String cropType);

    /* ==================== 新增 ==================== */
    @Insert("INSERT INTO t_market_analysis(" +
            "crop_type, analysis_date, current_price, predicted_price, " +
            "price_trend, market_demand, main_regions, influencing_factors, create_time" +
            ") VALUES (" +
            "#{cropType}, #{analysisDate}, #{currentPrice}, " +
            "#{predictedPrice}, #{priceTrend}, #{marketDemand}, " +
            "#{mainRegions}, #{influencingFactors}, NOW()" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "analysisId", keyColumn = "analysis_id")
    int insert(MarketAnalysis analysis);

    /* ==================== 修改 ==================== */
    @Update("UPDATE t_market_analysis SET " +
            "crop_type = #{cropType}, " +
            "analysis_date = #{analysisDate}, " +
            "current_price = #{currentPrice}, " +
            "predicted_price = #{predictedPrice}, " +
            "price_trend = #{priceTrend}, " +
            "market_demand = #{marketDemand}, " +
            "main_regions = #{mainRegions}, " +
            "influencing_factors = #{influencingFactors}, " +
            "WHERE analysis_id = #{analysisId}")
    int update(MarketAnalysis analysis);

    /* ==================== 删除 ==================== */
    @Delete("DELETE FROM t_market_analysis WHERE analysis_id = #{analysisId}")
    int deleteById(Long analysisId);
}