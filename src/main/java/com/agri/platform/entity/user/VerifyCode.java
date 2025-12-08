package com.agri.platform.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCode {
    public enum BizType {
        REGISTER,
        LOGIN,
        RESET_PASSWORD
    }

    private long id;
    private BizType bizType;
    private String target;
    private String code;
    private LocalDateTime expireTime;
    private Boolean used;
    private LocalDateTime createdTime;
}
