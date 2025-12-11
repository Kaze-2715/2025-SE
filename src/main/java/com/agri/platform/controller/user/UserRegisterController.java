package com.agri.platform.controller.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agri.platform.DTO.UserRegisterDTO;
import com.agri.platform.entity.user.User;
import com.agri.platform.service.user.UserRegisterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserRegisterController {
    private final UserRegisterService userRegisterService;

    @PostMapping("/register")
    public User register(@RequestBody UserRegisterDTO dto) {
        return userRegisterService.registerUser(dto);
    }
}
