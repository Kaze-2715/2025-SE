package com.agri.platform.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.agri.platform.entity.user.LoginLog;

@Mapper
public interface UserLoginMapper {
    @Insert("INSERT INTO t_login_log(user_id,login_time,login_ip,user_agent,success,fail_reason) " +
            "VALUES(#{l.userId},#{l.loginTime},#{l.loginIp},#{l.userAgent},#{l.success},#{l.failReason})")
    int insertLog(@Param("l") LoginLog log);

    @Select("SELECT log_id, user_id, login_time, login_ip, user_agent, success, fail_reason FROM t_login_log ORDER BY log_id")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "log_id", property = "logId"),
            @Result(column = "login_time", property = "loginTime"),
            @Result(column = "login_ip", property = "loginIp"),
            @Result(column = "user_agent", property = "userAgent"),
            @Result(column = "success", property = "success"),
            @Result(column = "fail_reason", property = "failReason")
    })
    List<LoginLog> selectAll();

    @Delete("DELETE FROM t_login_log")
    int deleteAll();

    @Select("SELECT COUNT(*) FROM t_login_log")
    long count();
}
