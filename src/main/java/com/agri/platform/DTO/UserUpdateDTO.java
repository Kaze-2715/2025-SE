package com.agri.platform.DTO;

import java.time.LocalDateTime;

public record UserUpdateDTO(
        String userId,
        String username,
        String phoneNumber,
        String email,
        String accountStatus,
        LocalDateTime lastLoginTime,
        String lastLoginIp,
        Integer loginFailCount,
        LocalDateTime loginLockedUntil) {
}
