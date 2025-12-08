package com.agri.platform.controller.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agri.platform.DTO.UserLoginDTO;
import com.agri.platform.entity.user.User;
import com.agri.platform.exception.BizException;
import com.agri.platform.service.user.UserLoginService;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class UserLoginController {
    private final UserLoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO loginDTO, HttpServletRequest request) {
        try {
            String clientIp = getClientIp(request);
            loginDTO = new UserLoginDTO(loginDTO.login(), loginDTO.password(), loginDTO.type(), clientIp);

            User user = loginService.loginUser(loginDTO);

            LoginResponse response = new LoginResponse(user.getUserId(), user.getUsername(), user.getEmail(), user.getPhoneNumber(), user.getLastLoginTime(), "登录成功");
            return ResponseEntity.ok(response);
        } catch (BizException e) {
            log.warn("登录业务异常：{}", e.getMessage());
            return ResponseEntity.status(e.getStatus()).body(new ErrorResponse("Error", e.getMessage()));
        } catch (Exception e) {
            log.error("登录系统异常", e);   
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error", "系统异常，请稍后再试"));
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    record LoginResponse(
            String userId,
            String username,
            String email,
            String phoneNumber,
            LocalDateTime lastLoginTime,
            String message) {
    }
    
    public record ErrorResponse(
            String code,
            String message
    ) {
    }
}
