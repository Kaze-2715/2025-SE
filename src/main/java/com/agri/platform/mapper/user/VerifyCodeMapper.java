package com.agri.platform.mapper.user;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.type.EnumTypeHandler;

import com.agri.platform.entity.user.VerifyCode;

public interface VerifyCodeMapper {
        @Results(id = "verifyCodeMap", value = {
                        @Result(property = "bizType", column = "biz_type", javaType = VerifyCode.BizType.class, typeHandler = EnumTypeHandler.class)
        })

        @Insert("""
                        INSERT INTO t_verify_code (biz_type, target, code, expire_time)
                        VALUES (#{bizType}, #{target}, #{code}, #{expireTime})
                        ON DUPLICATE KEY UPDATE
                            code = #{code}, expire_time = #{expireTime}, used = 0
                        """)
        @ResultMap("verifyCodeMap")
        void saveCode(@Param("bizType") String bizType,
                        @Param("target") String target,
                        @Param("code") String code,
                        @Param("expireTime") LocalDateTime expireTime);

        @Select("""
                        SELECT * FROM t_verify_code WHERE biz_type = #{bizType} AND target = #{target} AND used = 0
                        ORDER BY created_time DESC LIMIT 1
                        """)
        @ResultMap("verifyCodeMap")
        VerifyCode findLatestCode(@Param("bizType") String bizType,
                        @Param("target") String target);

        @Update("UPDATE t_verify_code SET used = 1 WHERE id = #{id}")
        void markUsed(@Param("id") Long id);

        @Delete("DELETE FROM t_verify_code WHERE expire_time < NOW()")
        int deleteExpiredCodes(LocalDateTime now);
}
