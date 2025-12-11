package com.agri.platform.entity.user;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public enum AccountStatus {
        ACTIVE,
        PENDING,
        FROZEN
    }

    private String userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String passwordHash;
    private AccountStatus accountStatus;
    private LocalDateTime registrationTime;
    private LocalDateTime lastLoginTime;
    private String lastLoginIP;
    private Integer loginFailCount;
    private LocalDateTime loginLockedUntil;

}
