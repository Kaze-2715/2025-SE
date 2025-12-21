// src/main/java/com/agri/platform/mapper/analysis/EcommerceDataMapper.java
package com.agri.platform.mapper.analysis;

import com.agri.platform.entity.analysis.EcommerceData;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface EcommerceDataMapper {
    /* ==================== 查询 ==================== */
    @Select("SELECT data_id, crop_type, platform_name, price, sales_volume, " +
            "comment_count, positive_rate, crawl_time " +
            "FROM t_ecommerce_data " +
            "WHERE data_id = #{dataId}")
    @Results(id = "ecommerceDataMap", value = {
            @Result(column = "data_id", property = "dataId", jdbcType = JdbcType.BIGINT),
            @Result(column = "crop_type", property = "cropType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "platform_name", property = "platformName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "price", property = "price", jdbcType = JdbcType.DECIMAL),
            @Result(column = "sales_volume", property = "salesVolume", jdbcType = JdbcType.INTEGER),
            @Result(column = "comment_count", property = "commentCount", jdbcType = JdbcType.INTEGER),
            @Result(column = "positive_rate", property = "positiveRate", jdbcType = JdbcType.DECIMAL),
            @Result(column = "crawl_time", property = "crawlTime", jdbcType = JdbcType.TIMESTAMP)
    })
    EcommerceData selectById(Long dataId);

    @ResultMap("ecommerceDataMap")
    @Select("SELECT data_id, crop_type, platform_name, price, sales_volume, " +
            "comment_count, positive_rate, crawl_time " +
            "FROM t_ecommerce_data " +
            "WHERE crop_type = #{cropType} " +
            "ORDER BY crawl_time DESC")
    List<EcommerceData> selectByCropType(String cropType);

    @ResultMap("ecommerceDataMap")
    @Select("SELECT data_id, crop_type, platform_name, price, sales_volume, " +
            "comment_count, positive_rate, crawl_time " +
            "FROM t_ecommerce_data " +
            "WHERE platform_name = #{platformName} " +
            "ORDER BY crawl_time DESC")
    List<EcommerceData> selectByPlatform(String platformName);

    /* ==================== 单条插入 ==================== */
    @Insert("INSERT INTO t_ecommerce_data(" +
            "crop_type, platform_name, price, sales_volume, " +
            "comment_count, positive_rate, crawl_time" +
            ") VALUES (" +
            "#{cropType}, #{platformName}, #{price}, " +
            "#{salesVolume}, #{commentCount}, #{positiveRate}, NOW()" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "dataId", keyColumn = "data_id")
    int insert(EcommerceData data);

    /* ==================== 批量插入 ==================== */
    @Insert("<script>" +
            "INSERT INTO t_ecommerce_data(" +
            "crop_type, platform_name, price, sales_volume, " +
            "comment_count, positive_rate, crawl_time" +
            ") VALUES " +
            "<foreach collection='dataList' item='d' separator=','>" +
            "(#{d.cropType}, #{d.platformName}, #{d.price}, " +
            "#{d.salesVolume}, #{d.commentCount}, #{d.positiveRate}, NOW())" +
            "</foreach>" +
            "</script>")
    int batchInsert(@Param("dataList") List<EcommerceData> dataList);

    /* ==================== 清理老数据 ==================== */
    @Delete("DELETE FROM t_ecommerce_data WHERE crawl_time < #{time}")
    int deleteOlderThan(LocalDateTime time);
}