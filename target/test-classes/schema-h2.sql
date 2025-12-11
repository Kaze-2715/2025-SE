-- 用户
CREATE TABLE t_user (
  user_id            VARCHAR(64) PRIMARY KEY,
  username           VARCHAR(50),
  password_hash      VARCHAR(255) NOT NULL,
  account_status     ENUM('PENDING','ACTIVE','FROZEN') DEFAULT 'ACTIVE',
  last_login_time     TIMESTAMP,
    last_login_ip       VARCHAR(45),
    login_fail_count    INT DEFAULT 0,
    login_locked_until  TIMESTAMP
);

-- 角色
CREATE TABLE t_role (
  role_id   VARCHAR(64) PRIMARY KEY,
  role_name VARCHAR(50) NOT NULL UNIQUE
);

-- 权限
CREATE TABLE t_permission (
  permission_id VARCHAR(64) PRIMARY KEY,
  perm_name     VARCHAR(100) NOT NULL
);

-- 关联
CREATE TABLE t_user_role (
  user_id       VARCHAR(64),
  role_id       VARCHAR(64),
  assigned_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id, role_id)
);

CREATE TABLE t_role_permission (
  role_id       VARCHAR(64),
  permission_id VARCHAR(64),
  PRIMARY KEY (role_id, permission_id)
);