package com.agri.platform.mapper.planning;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.type.JdbcType;

import com.agri.platform.entity.planning.PestWarning;

import java.util.List;

@Mapper
public interface PestWarningMapper {

    /* ====== 查询 ====== */
    @Results(id = "pestWarningMap", value = {
            @Result(column = "warning_id", property = "warningId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "land_id", property = "landId", jdbcType = JdbcType.BIGINT),
            @Result(column = "warning_type", property = "warningType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "warning_level", property = "warningLevel", jdbcType = JdbcType.INTEGER),
            @Result(column = "handle_status", property = "handleStatus", jdbcType = JdbcType.INTEGER),
            @Result(column = "description", property = "description", jdbcType = JdbcType.VARCHAR),
            @Result(column = "warning_reason", property = "warningReason", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    @Select("SELECT * FROM pest_warning WHERE handle_status = #{status}")
    List<PestWarning> selectByHandleStatus(Integer status);

    @ResultMap("pestWarningMap")
    @Select("SELECT * FROM pest_warning WHERE warning_id = #{warningId}")
    PestWarning selectById(Long warningId);

    @ResultMap("pestWarningMap")
    @Select("SELECT * FROM pest_warning WHERE warning_level = #{level}")
    List<PestWarning> selectByWarningLevel(Integer level);

    @ResultMap("pestWarningMap")
    @Select("SELECT * FROM pest_warning")
    List<PestWarning> selectAll();

    /* ====== 增删改 ====== */
    @Insert("INSERT INTO pest_warning(" +
            "land_id, warning_type, warning_level, handle_status, " +
            "description, warning_reason, create_time, update_time) " +
            "VALUES(#{landId}, #{warningType}, #{warningLevel}, #{handleStatus}, " +
            "#{description}, #{warningReason}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "warningId")
    int insert(PestWarning warning);

    @Update("UPDATE pest_warning SET " +
            "land_id = #{landId}, " +
            "warning_type = #{warningType}, " +
            "warning_level = #{warningLevel}, " +
            "handle_status = #{handleStatus}, " +
            "description = #{description}, " +
            "warning_reason = #{warningReason}, " +
            "update_time = #{updateTime} " +
            "WHERE warning_id = #{warningId}")
    int update(PestWarning warning);

    @Delete("DELETE FROM pest_warning WHERE warning_id = #{warningId}")
    int deleteById(Long warningId);
}