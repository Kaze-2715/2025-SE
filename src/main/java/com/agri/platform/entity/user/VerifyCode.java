package com.agri.platform.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCode {
    public enum BizType {
        REGISTER,
        LOGIN,
        RESET_PASSWORD
    }

    private long id;
    private String bizType;
    private String target;
    private String code;
    private LocalDateTime expireTime;
    private LocalDateTime createdTime;
    private Boolean used;
}
