package com.agri.platform.DTO;

import com.agri.platform.enums.UserLoginType;

public record UserRegisterDTO(
        UserLoginType type,
        String login,
        String password) {
    public UserRegisterDTO {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("用户名、邮箱和密码至少填一项！");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("密码不能为空！");
        }
    }
}
