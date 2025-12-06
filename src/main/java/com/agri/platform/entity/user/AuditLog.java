package com.agri.platform.entity.user;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {
    private long logId;
    private String userId;
    private String operation;
    private String targetType;
    private String targetId;
    private LocalDateTime operateTime;
    private String ip;
}
