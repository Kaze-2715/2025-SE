-- 角色
INSERT INTO t_role(role_id, role_name) VALUES ('role_user', '普通用户');

-- 权限
INSERT INTO t_permission(permission_id, perm_name) VALUES ('perm_farm_view', '农场-查看');

-- 绑定
INSERT INTO t_role_permission(role_id, permission_id) VALUES ('role_user', 'perm_farm_view');

-- 用户（明文密码 = 123456，BCrypt 加密后）
INSERT INTO t_user(user_id, username, password_hash, account_status)
VALUES ('u123', 'Test@123456', '$2a$10$/bpb0Okb7Kize3EG7KYvrespW0BK1wVqP8yFzIBMJl4vwmFcAwOXO', 'ACTIVE');

-- 给用户角色
INSERT INTO t_user_role(user_id, role_id) VALUES ('u123', 'role_user');