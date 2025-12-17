package com.agri.platform.DTO;

public record UserUpdateDTO(
        String userId,
        String username,
        String phoneNumber,
        String email
        ) {
}
