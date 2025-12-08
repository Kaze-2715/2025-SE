package com.agri.platform.mapper.user;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

import com.agri.platform.DTO.UserUpdateDTO;
import com.agri.platform.entity.user.User;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM t_user WHERE user_id = #{userId}")
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
    Optional<User> selectById(String userId);

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

    @ResultMap("userMap")
    @Select("SELECT * FROM t_user WHERE username = #{username}")
    Optional<User> selectByUsername(String username);

    @ResultMap("userMap")
    @Select("SELECT * FROM t_user WHERE email = #{email}")
    Optional<User> selectByEmail(String email);
    
    @Select("SELECT * FROM t_user WHERE phone_number = #{phoneNumber}")
    Optional<User> selectByPhoneNumber(String phoneNumber);

    @Select("SELECT DISTINCT p.perm_name " +
            "FROM t_user_role ur " +
            "JOIN t_role_permission rp ON ur.role_id = rp.role_id " +
            "JOIN t_permission p ON rp.permission_id = p.permission_id " +
            "WHERE ur.user_id = #{userId}")
    List<String> listPermCodeByUserId(@Param("userId") String userId);

    @Update("""
            UPDATE t_user
            SET last_login_time = #{lastLoginTime},
                last_login_ip = #{lastLoginIP},
                login_fail_count = #{loginFailCount},
                login_locked_until = #{loginLockedUntil}
            WHERE user_id = #{userId}
            """)
    void updateLoginInfo(User user);

    @UpdateProvider(type = UserMapper.UserSqlProvider.class, method = "updateByIdSelective")
    int updateByIdSelective(UserUpdateDTO dto);

    class UserSqlProvider {
        public String updateByIdSelective(UserUpdateDTO u) {
            return new SQL() {
                {
                    UPDATE("t_user");
                    if (u.username() != null)
                        SET("username = #{username}");
                    if (u.phoneNumber() != null)
                        SET("phone_number = #{phoneNumber}");
                    if (u.email() != null)
                        SET("email = #{email}");
                    WHERE("user_id = #{userId}");
                }
            }.toString();
        }
    }
}