package com.agri.platform.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.EnumTypeHandler;

import com.agri.platform.entity.User;

@Mapper
public interface UserMapper {
    @Results(id = "userMap", value = {
            @Result(property = "accountStatus", column = "account_status", javaType = User.AccountStatus.class, typeHandler = EnumTypeHandler.class)
    })

    @Insert("""
            INSERT INTO t_user (username, password, email, phone_number)
            VALUES (#{username}, #{password}, #{email}, #{phoneNumber})
            """)
    @Options(useGeneratedKeys = false)
    int insertUser(User user);

    @Select("SELECT COUNT(*) FROM t_user WHERE username = #{username}")
    int countByUsername(String username);

    @Select("SELECT COUNT(*) FROM t_user WHERE email = #{email}")
    int countByEmail(String email);

    @Select("SELECT COUNT(*) FROM t_user WHERE phone_number = #{phoneNumber}")
    int countByPhoneNumber(String phoneNumber);

    @Select("SELECT * FROM t_user WHERE username = #{username}")
    @ResultMap("userMap")
    Optional<User> selectByUsername(String username);

    @Select("SELECT * FROM t_user WHERE email = #{email}")
    @ResultMap("userMap")
    Optional<User> selectByEmail(String email);

    @Select("SELECT * FROM t_user WHERE phone_number = #{phoneNumber}")
    @ResultMap("userMap")
    Optional<User> selectByPhoneNumber(String phoneNumber);

    @Update("""
            UPDATE t_user 
            SET last_login_time = #{lastLoginTime},
                last_login_ip = #{lastLoginIP},
                login_fail_count = #{loginFailCount},
                login_locked_until = #{loginLockedUntil}
            WHERE user_id = #{userId}
            """)
    void updateLoginInfo(User user);
}
