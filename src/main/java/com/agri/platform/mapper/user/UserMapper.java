package com.agri.platform.mapper.user;

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

import com.agri.platform.entity.user.User;

@Mapper
public interface UserMapper {
//         @Results(id = "userMap", value = {
//                         @Result(property = "userId", column = "user_id"),
//                         @Result(property = "username", column = "username"),
//                         @Result(property = "phoneNumber", column = "phone_number"),
//                         @Result(property = "email", column = "email"),
//                         @Result(property = "passwordHash", column = "password_hash"),
//                         @Result(property = "accountStatus", column = "account_status", javaType = User.AccountStatus.class, typeHandler = org.apache.ibatis.type.EnumTypeHandler.class),
//                         @Result(property = "registrationTime", column = "registration_time"),
//                         @Result(property = "lastLoginTime", column = "last_login_time"),
//                         @Result(property = "lastLoginIP", column = "last_login_ip"),
//                         @Result(property = "loginFailCount", column = "login_fail_count"),
//                         @Result(property = "loginLockedUntil", column = "login_locked_until")
//         })
//     @Select("SELECT * FROM t_user LIMIT 1")
//     void userMap();

    @Insert("""
            INSERT INTO t_user (user_id, username, password_hash, email, phone_number)
            VALUES (#{userId}, #{username}, #{passwordHash}, #{email}, #{phoneNumber})
            """)
    @Options(useGeneratedKeys = false)
    int insertUser(User user);

    @Select("SELECT COUNT(*) FROM t_user WHERE username = #{username}")
    int countByUsername(String username);

    @Select("SELECT COUNT(*) FROM t_user WHERE email = #{email}")
    int countByEmail(String email);

    @Select("SELECT COUNT(*) FROM t_user WHERE phone_number = #{phoneNumber}")
    int countByPhoneNumber(String phoneNumber);

    // @ResultMap("userMap")
    @Select("SELECT * FROM t_user WHERE username = #{username}")
    Optional<User> selectByUsername(String username);

    //     @ResultMap("userMap")
    @Results(id = "userMap", value = {
                    @Result(property = "userId", column = "user_id"),
                    @Result(property = "username", column = "username"),
                    @Result(property = "phoneNumber", column = "phone_number"),
                    @Result(property = "email", column = "email"),
                    @Result(property = "passwordHash", column = "password_hash"),
                    @Result(property = "accountStatus", column = "account_status", javaType = User.AccountStatus.class, typeHandler = org.apache.ibatis.type.EnumTypeHandler.class),
                    @Result(property = "registrationTime", column = "registration_time"),
                    @Result(property = "lastLoginTime", column = "last_login_time"),
                    @Result(property = "lastLoginIP", column = "last_login_ip"),
                    @Result(property = "loginFailCount", column = "login_fail_count"),
                    @Result(property = "loginLockedUntil", column = "login_locked_until")
    })
    @Select("SELECT * FROM t_user WHERE email = #{email}")
    Optional<User> selectByEmail(String email);

    // @ResultMap("userMap")
    @Select("SELECT * FROM t_user WHERE phone_number = #{phoneNumber}")
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
