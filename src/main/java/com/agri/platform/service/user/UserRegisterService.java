package com.agri.platform.service.user;

import org.springframework.stereotype.Service;

import java.util.UUID;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.agri.platform.DTO.UserRegisterDTO;
import com.agri.platform.entity.user.User;
import com.agri.platform.exception.BizException;
import com.agri.platform.mapper.user.UserMapper;
import com.agri.platform.enums.UserLoginType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserRegisterService {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final VerifyCodeService verifyCodeService;

    public User registerUser(UserRegisterDTO dto) {
        if (dto.login() == null || dto.login().isBlank()) {
            throw new BizException(HttpStatus.BAD_REQUEST,"用户名、邮箱或手机号至少填一项！");
        }
        if (dto.password() == null || dto.password().isBlank()) {
            throw new BizException(HttpStatus.BAD_REQUEST,"密码不能为空！");
        }

        boolean exists = switch (dto.type()) {
            case USERNAME -> userMapper.countByUsername(dto.login()) > 0;
            case EMAIL -> userMapper.countByEmail(dto.login()) > 0;
            case PHONE_NUMBER -> userMapper.countByPhoneNumber(dto.login()) > 0;
        };
        if (exists) {
            throw new BizException(HttpStatus.BAD_REQUEST, "用户已存在！");
        }
        
        if (dto.type() == UserLoginType.EMAIL) {
            boolean ok = verifyCodeService.verify(dto.login(), "REGISTER", dto.verifyCode());
            if (!ok) {
                throw new BizException(HttpStatus.BAD_REQUEST, "无效的验证码！");
            }
        }

        User user = User.builder()
                .userId(UUID.randomUUID().toString())
                .username(dto.type() == UserLoginType.USERNAME ? dto.login() : null)
                .email(dto.type() == UserLoginType.EMAIL ? dto.login() : null)
                .phoneNumber(dto.type() == UserLoginType.PHONE_NUMBER ? dto.login() : null)
                .passwordHash(encoder.encode(dto.password()))
                .accountStatus(User.AccountStatus.PENDING)
                .registrationTime(LocalDateTime.now())
                .loginFailCount(0)
                .loginLockedUntil(null)
                .build();

        userMapper.insertUser(user);
        log.info("Registered new user: {}", user.getUserId());
        return user;
    }
}
