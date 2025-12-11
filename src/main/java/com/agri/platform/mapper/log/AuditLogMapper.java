package com.agri.platform.mapper.log;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import com.agri.platform.entity.log.AuditLog;

@Mapper
public interface AuditLogMapper {
    @Insert("""
        INSERT INTO t_audit_log (user_id, operation, target_type, target_id, ip)
        VALUES (#{log.userId}, #{log.operation}, #{log.targetType}, #{log.targetId}, #{log.ip})""")
    void insertAuditLog(@Param("log") AuditLog log);
        
    @Results(id = "auditLogMap", value = {
            @Result(column = "log_id",        property = "logId",      jdbcType = JdbcType.BIGINT),
            @Result(column = "user_id",       property = "userId",     jdbcType = JdbcType.VARCHAR),
            @Result(column = "operation",     property = "operation",  jdbcType = JdbcType.VARCHAR),
            @Result(column = "target_type",   property = "targetType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "target_id",     property = "targetId",   jdbcType = JdbcType.VARCHAR),
            @Result(column = "operate_time",  property = "operateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "ip",            property = "ip",         jdbcType = JdbcType.VARCHAR)
    })
    @Select("SELECT * FROM t_audit_log WHERE id = #{logId}")
    AuditLog getAuditLogById(@Param("id") Long id);

    @Select("SELECT * FROM t_audit_log WHERE user_id = #{userId}")
    @ResultMap("auditLogMap")
    List<AuditLog> getAuditLogsByUserId(@Param("userId") String userId);

    @Select("SELECT * FROM t_audit_log WHERE target_type = #{targetType} AND target_id = #{targetId}")
    @ResultMap("auditLogMap")
    List<AuditLog> getAuditLogsByTarget(@Param("targetType") String targetType, @Param("targetId") Long targetId);

    @Select("SELECT * FROM t_audit_log WHERE operation = #{operation}")
    @ResultMap("auditLogMap")
    List<AuditLog> getAuditLogsByOperation(@Param("operation") String operation);

    @Select("SELECT * FROM t_audit_log WHERE ip = #{ip}")
    @ResultMap("auditLogMap")
    List<AuditLog> getAuditLogsByIp(@Param("ip") String ip);

    @Select("SELECT * FROM t_audit_log WHERE created_at >= #{startTime} AND created_at <= #{endTime}")
    @ResultMap("auditLogMap")
    List<AuditLog> getAuditLogsByTimeRange(@Param("startTime") String startTime, @Param("endTime") String endTime);

    @Select("SELECT * FROM t_audit_log")
    @ResultMap("auditLogMap")
    List<AuditLog> listAllAuditLogs();
}
