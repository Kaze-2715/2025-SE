package com.agri.platform.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginLog {
    private long logId;
    private String userId;
    private LocalDateTime loginTime;
    private String loginIp;
    private String userAgent;
    private boolean success;
    private String failReason;
}
