package com.agri.platform.DTO;

import com.agri.platform.enums.UserLoginType;

public record UserLoginDTO(String login, String password, UserLoginType type, String ip) {
    public UserLoginDTO {
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("账号或密码不能为空！");
        }
    }
}
