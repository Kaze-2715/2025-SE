// src/main/java/com/agri/platform/mapper/analysis/YieldPredictionModelMapper.java
package com.agri.platform.mapper.analysis;

import com.agri.platform.entity.analysis.YieldPredictionModel;

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
public interface YieldPredictionModelMapper {
    /* ==================== 查询 ==================== */
    @Select("SELECT model_id, crop_type, model_name, description, algorithm_type, " +
            "create_time, update_time, accuracy, status " +
            "FROM t_yield_prediction_model " +
            "WHERE model_id = #{modelId}")
    @Results(id = "yieldPredictionModelMap", value = {
            @Result(column = "model_id", property = "modelId", jdbcType = JdbcType.BIGINT),
            @Result(column = "crop_type", property = "cropType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "model_name", property = "modelName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "description", property = "description", jdbcType = JdbcType.VARCHAR),
            @Result(column = "algorithm_type", property = "algorithmType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "accuracy", property = "accuracy", jdbcType = JdbcType.DOUBLE),
            @Result(column = "status", property = "status", jdbcType = JdbcType.VARCHAR)
    })
    YieldPredictionModel selectById(Long modelId);

    @ResultMap("yieldPredictionModelMap")
    @Select("SELECT model_id, crop_type, model_name, description, algorithm_type, " +
            "create_time, update_time, accuracy, status " +
            "FROM t_yield_prediction_model " +
            "WHERE crop_type = #{cropType} " +
            "ORDER BY accuracy DESC, model_name ASC")
    List<YieldPredictionModel> selectByCropType(String cropType);

    @ResultMap("yieldPredictionModelMap")
    @Select("SELECT model_id, crop_type, model_name, description, algorithm_type, " +
            "create_time, update_time, accuracy, status " +
            "FROM t_yield_prediction_model " +
            "ORDER BY crop_type, accuracy DESC")
    List<YieldPredictionModel> selectAll();

    /* ==================== 新增 ==================== */
    @Insert("INSERT INTO t_yield_prediction_model(" +
            "crop_type, model_name, description, algorithm_type, " +
            "accuracy, status, create_time, update_time" +
            ") VALUES (" +
            "#{cropType}, #{modelName}, #{description}, #{algorithmType}, " +
            "#{accuracy}, #{status}, NOW(), NOW()" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "modelId", keyColumn = "model_id")
    int insert(YieldPredictionModel model);

    /* ==================== 修改 ==================== */
    @Update("UPDATE t_yield_prediction_model SET " +
            "crop_type = #{cropType}, " +
            "model_name = #{modelName}, " +
            "description = #{description}, " +
            "algorithm_type = #{algorithmType}, " +
            "accuracy = #{accuracy}, " +
            "status = #{status}, " +
            "update_time = NOW() " +
            "WHERE model_id = #{modelId}")
    int update(YieldPredictionModel model);

    /* ==================== 删除 ==================== */
    @Delete("DELETE FROM t_yield_prediction_model WHERE model_id = #{modelId}")
    int deleteById(Long modelId);
}