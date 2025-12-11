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
import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

import com.agri.platform.DTO.UserRoleDTO;
import com.agri.platform.DTO.UserUpdateDTO;
import com.agri.platform.DTO.RolePermDTO;
import com.agri.platform.entity.user.User;
import com.agri.platform.entity.user.Role;
import com.agri.platform.entity.user.Permission;

@Mapper
public interface UserMapper {
    // 用户相关操作
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
            SET account_status = #{accountStatus}
            WHERE user_id = #{userId}
            """)
    void updateAccountStatus(User user);

    @Update("""
            UPDATE t_user
            SET last_login_time = #{lastLoginTime},
                last_login_ip = #{lastLoginIP},
                login_fail_count = #{loginFailCount},
                login_locked_until = #{loginLockedUntil}
            WHERE user_id = #{userId}
            """)
    void updateLoginInfo(User user);

    // 角色与权限相关操作

    @Select("""
            SELECT u.user_id, u.username,
                   r.role_id, r.role_name
            FROM t_user u
            JOIN t_user_role ur ON u.user_id = ur.user_id
            JOIN t_role r ON ur.role_id = r.role_id
            """)
    @ConstructorArgs({
            @Arg(column = "user_id", javaType = String.class),
            @Arg(column = "username", javaType = String.class),
            @Arg(column = "role_id", javaType = String.class),
            @Arg(column = "role_name", javaType = String.class)
    })
    List<UserRoleDTO> listUserRole();

    @Select("SELECT COUNT(*) FROM t_user_role WHERE user_id = #{userId} AND role_id = #{roleId}")
    int countUserRole(@Param("userId") String userId, @Param("roleId") String roleId);

    @Select("""
            SELECT r.role_id, r.role_name,  p.perm_id, p.perm_name
            FROM t_role r
            JOIN t_role_permission rp ON r.role_id = rp.role_id
            JOIN t_permission p ON rp.permission_id = p.permission_id
            """)
    @ConstructorArgs({
            @Arg(column = "role_id", javaType = String.class),
            @Arg(column = "role_name", javaType = String.class),
            @Arg(column = "perm_id", javaType = String.class),
            @Arg(column = "perm_name", javaType = String.class)
    })
    List<RolePermDTO> listRolePermission();

    @Insert("""
            INSERT INTO t_role(role_id, role_name, description)
            VALUES (#{roleId}, #{roleName}, #{description})
            """)
    int insertRole(Role role);

    @Select("""
            SELECT COUNT(*) FROM t_role
            WHERE role_name = #{roleName}""")
    int countByRoleName(String roleName);

    @UpdateProvider(type = UserMapper.UserSqlProvider.class, method = "updateByIdSelective")
    int updateByIdSelective(UserUpdateDTO dto);

    @Select("""
            SELECT role_id FROM t_role WHERE role_name = #{roleName} LIMIT 1""")
    String selectRoleIdByRoleName(String roleName);

    @Delete("""
            DELETE FROM t_role
            WHERE role_id = #{roleId}""")
    int deleteRoleById(String roleId);

    @Delete("""
            DELETE FROM t_role_permission
            WHERE role_id = #{roleId}
            """)
    int deleteRolePermissionByRoleId(String roleId);

    @Delete("""
            DELETE FROM t_user_role
            WHERE role_id = #{roleId}
            """)
    int deleteUserRoleByRoleId(String roleId);

    @Update("""
            UPDATE t_role
            SET description = #{description}
            WHERE role_name = #{roleName}
            """)
    int updateRole(@Param("roleName") String roleName, @Param("description") String description);

    /** 给用户绑角色（测试专用） */
    @Insert("INSERT INTO t_user_role(user_id, role_id) VALUES (#{userId}, #{roleId})")
    int insertUserRole(@Param("userId") String userId, @Param("roleId") String roleId);

    /** 按 ID 查角色实体（测试断言用） */
    @Select("SELECT * FROM t_role WHERE role_id = #{roleId}")
    Role selectRoleById(String roleId);

    // 权限相关操作
    @Insert("""
            INSERT INTO t_permission(permission_id, perm_name, description)
            VALUES (#{permissionId}, #{permName}, #{description})
            """)
    int addPermission(Permission permission);

    @Select("SELECT COUNT(*) FROM t_permission WHERE perm_name = #{permName}")
    int countByPermName(@Param("permName") String permName);

    @Select("SELECT permission_id FROM t_permission WHERE perm_name = #{permName} LIMIT 1")
    String selectPermissionIdByPermissionName(@Param("permName") String permName);

    @Select("SELECT * FROM t_permission WHERE permission_id = #{permissionId}")
    Permission selectPermissionById(String permissionId);

    @Delete("DELETE FROM t_permission WHERE permission_id = #{permissionId}")
    int deletePermissionById(@Param("permissionId") String permissionId);

    @Delete("DELETE FROM t_permission WHERE perm_name = #{permName}")
    int deletePermissionByName(@Param("permName") String permName);

    @Delete("DELETE FROM t_role_permission WHERE permission_id = #{permissionId}")
    int deleteRolePermissionByPermissionId(@Param("permissionId") String permissionId);

    @Update("UPDATE t_permission " +
            "SET description = #{description} " +
            "WHERE perm_name = #{permName}")
    int updatePermission(String permName, String description);

    @Select("""
            SELECT perm_name
            FROM t_permission ORDER BY perm_name
            """)
    List<String> listAllPermissions();

    @Select("""
            SELECT p.perm_name
            FROM t_role_permission rp
            JOIN t_permission p ON rp.permission_id = p.permission_id
            """)
    List<String> listPermNameByRoleId(String roleId);

    @Delete("""
            DELETE FROM t_role_permission
            WHERE role_id = #{roleId}
            """)
    int deleteRolePermissions(String roleId);

    @Select("SELECT permission_id FROM t_permission WHERE perm_name = #{permName}")
    Optional<String> selectIdByPermName(@Param("permName") String permName);

    @Insert("INSERT INTO t_role_permission(role_id, permission_id) VALUES (#{roleId}, #{permId})")
    int insertRolePermission(@Param("roleId") String roleId, @Param("permId") String permId);

    @Select("SELECT COUNT(*) FROM t_role_permission WHERE role_id = #{roleId} AND permission_id = #{permId}")
    int countRolePermission(@Param("roleId") String roleId, @Param("permId") String permId);

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