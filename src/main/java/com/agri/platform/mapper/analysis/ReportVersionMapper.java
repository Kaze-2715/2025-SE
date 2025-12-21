// src/main/java/com/agri/platform/mapper/analysis/ReportVersionMapper.java
package com.agri.platform.mapper.analysis;

import com.agri.platform.entity.analysis.ReportVersion;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportVersionMapper {
    /* ==================== 查询 ==================== */
    @Select("SELECT version_id, report_id, report_type, version_number, change_log, " +
            "created_by, create_time, is_current " +
            "FROM t_report_version " +
            "WHERE version_id = #{versionId}")
    @Results(id = "reportVersionMap", value = {
            @Result(column = "version_id", property = "versionId", jdbcType = JdbcType.BIGINT),
            @Result(column = "report_id", property = "reportId", jdbcType = JdbcType.BIGINT),
            @Result(column = "report_type", property = "reportType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "version_number", property = "versionNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "change_log", property = "changeLog", jdbcType = JdbcType.VARCHAR),
            @Result(column = "created_by", property = "createdBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "is_current", property = "isCurrent", jdbcType = JdbcType.TINYINT)
    })
    ReportVersion selectById(Long versionId);

    @ResultMap("reportVersionMap")
    @Select("SELECT version_id, report_id, report_type, version_number, change_log, " +
            "created_by, create_time, is_current " +
            "FROM t_report_version " +
            "WHERE report_id = #{reportId} AND report_type = #{reportType} " +
            "ORDER BY version_number DESC")
    List<ReportVersion> selectByReport(@Param("reportId") Long reportId,
            @Param("reportType") String reportType);

    @ResultMap("reportVersionMap")
    @Select("SELECT version_id, report_id, report_type, version_number, change_log, " +
            "created_by, create_time, is_current " +
            "FROM t_report_version " +
            "WHERE report_id = #{reportId} AND report_type = #{reportType} AND is_current = 1 " +
            "LIMIT 1")
    ReportVersion selectCurrentVersion(@Param("reportId") Long reportId,
            @Param("reportType") String reportType);

    /* ==================== 新增 ==================== */
    @Insert("INSERT INTO t_report_version(" +
            "report_id, report_type, version_number, change_log, " +
            "created_by, create_time, is_current" +
            ") VALUES (" +
            "#{reportId}, #{reportType}, #{versionNumber}, #{changeLog}, " +
            "#{createdBy}, NOW(), #{isCurrent}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "versionId", keyColumn = "version_id")
    int insert(ReportVersion version);

    /* ==================== 修改 ==================== */
    @Update("UPDATE t_report_version SET " +
            "report_id = #{reportId}, " +
            "report_type = #{reportType}, " +
            "version_number = #{versionNumber}, " +
            "change_log = #{changeLog}, " +
            "created_by = #{createdBy}, " +
            "create_time = #{createTime}, " +
            "is_current = #{isCurrent} " +
            "WHERE version_id = #{versionId}")
    int update(ReportVersion version);

    /* ==================== 批量置为非当前 ==================== */
    @Update("UPDATE t_report_version " +
            "SET is_current = 0 " +
            "WHERE report_id = #{reportId} AND report_type = #{reportType}")
    int updateCurrentStatus(@Param("reportId") Long reportId,
            @Param("reportType") String reportType,
            @Param("isCurrent") Boolean isCurrent);
}