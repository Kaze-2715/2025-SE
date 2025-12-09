CREATE TABLE IF NOT EXISTS t_login_log (
  log_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id     VARCHAR(64)  NOT NULL,
  login_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  login_ip    VARCHAR(45)  NOT NULL,
  user_agent  VARCHAR(500),
  success     TINYINT      NOT NULL,
  fail_reason VARCHAR(100)
);