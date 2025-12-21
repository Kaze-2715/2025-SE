// src/main/java/com/agri/platform/mapper/analysis/YieldPredictionMapper.java
package com.agri.platform.mapper.analysis;

import com.agri.platform.entity.analysis.YieldPrediction;

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
public interface YieldPredictionMapper {
    /* ==================== 查询 ==================== */
    @Select("SELECT prediction_id, plan_id, model_id, crop_type, predicted_yield, " +
            "actual_yield, confidence, prediction_date, harvest_date, factors, " +
            "prediction_type, create_time, update_time " +
            "FROM t_yield_prediction " +
            "WHERE prediction_id = #{predictionId}")
    @Results(id = "yieldPredictionMap", value = {
            @Result(column = "prediction_id", property = "predictionId", jdbcType = JdbcType.BIGINT),
            @Result(column = "plan_id", property = "planId", jdbcType = JdbcType.BIGINT),
            @Result(column = "model_id", property = "modelId", jdbcType = JdbcType.BIGINT),
            @Result(column = "crop_type", property = "cropType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "predicted_yield", property = "predictedYield", jdbcType = JdbcType.DECIMAL),
            @Result(column = "actual_yield", property = "actualYield", jdbcType = JdbcType.DECIMAL),
            @Result(column = "confidence", property = "confidence", jdbcType = JdbcType.DECIMAL),
            @Result(column = "prediction_date", property = "predictionDate", jdbcType = JdbcType.DATE),
            @Result(column = "harvest_date", property = "harvestDate", jdbcType = JdbcType.DATE),
            @Result(column = "factors", property = "factors", jdbcType = JdbcType.VARCHAR),
            @Result(column = "prediction_type", property = "predictionType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    YieldPrediction selectById(Long predictionId);

    @ResultMap("yieldPredictionMap")
    @Select("SELECT prediction_id, plan_id, model_id, crop_type, predicted_yield, " +
            "actual_yield, confidence, prediction_date, harvest_date, factors, " +
            "prediction_type, create_time, update_time " +
            "FROM t_yield_prediction " +
            "WHERE plan_id = #{planId} " +
            "ORDER BY prediction_date DESC")
    List<YieldPrediction> selectByPlanId(Long planId);

    @ResultMap("yieldPredictionMap")
    @Select("SELECT prediction_id, plan_id, model_id, crop_type, predicted_yield, " +
            "actual_yield, confidence, prediction_date, harvest_date, factors, " +
            "prediction_type, create_time, update_time " +
            "FROM t_yield_prediction " +
            "WHERE plan_id IN (SELECT plan_id FROM t_planting_plan WHERE farmer_id = #{farmerId}) " +
            "ORDER BY prediction_date DESC")
    List<YieldPrediction> selectByFarmerId(Long farmerId);

    /* ==================== 新增 ==================== */
    @Insert("INSERT INTO t_yield_prediction(" +
            "plan_id, model_id, crop_type, predicted_yield, actual_yield, " +
            "confidence, prediction_date, harvest_date, factors, prediction_type, " +
            "create_time, update_time" +
            ") VALUES (" +
            "#{planId}, #{modelId}, #{cropType}, #{predictedYield}, NULL, " +
            "#{confidence}, #{predictionDate}, #{harvestDate}, #{factors}, 'AUTO', " +
            "NOW(), NOW()" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "predictionId", keyColumn = "prediction_id")
    int insert(YieldPrediction prediction);

    /* ==================== 修改 ==================== */
    @Update("UPDATE t_yield_prediction SET " +
            "plan_id = #{planId}, " +
            "model_id = #{modelId}, " +
            "crop_type = #{cropType}, " +
            "predicted_yield = #{predictedYield}, " +
            "actual_yield = #{actualYield}, " +
            "confidence = #{confidence}, " +
            "prediction_date = #{predictionDate}, " +
            "harvest_date = #{harvestDate}, " +
            "factors = #{factors}, " +
            "prediction_type = #{predictionType}, " +
            "update_time = NOW() " +
            "WHERE prediction_id = #{predictionId}")
    int update(YieldPrediction prediction);

    /* ==================== 删除 ==================== */
    @Delete("DELETE FROM t_yield_prediction WHERE prediction_id = #{predictionId}")
    int deleteById(Long predictionId);
}