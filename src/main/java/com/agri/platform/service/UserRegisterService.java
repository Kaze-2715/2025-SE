package com.agri.platform.service;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.agri.platform.entity.User;
import com.agri.platform.DTO.UserLoginDTO;
import com.agri.platform.DTO.UserRegisterDTO;
import com.agri.platform.exception.BizException;
import com.agri.platform.mapper.UserMapper;
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

    public User registerUser(UserRegisterDTO dto) {
        if (dto.login() == null || dto.login().isBlank()) {
            throw new BizException("用户名、邮箱或手机号至少填一项！");
        }
        if (dto.password() == null || dto.password().isBlank()) {
            throw new BizException("密码不能为空！");
        }

        boolean exists = switch (dto.type()) {
            case USERNAME -> userMapper.countByUsername(dto.login()) > 0;
            case EMAIL -> userMapper.countByEmail(dto.login()) > 0;
            case PHONE_NUMBER -> userMapper.countByPhoneNumber(dto.login()) > 0;
        };
        if (exists) {
            throw new BizException("用户已存在！");
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

    public User loginUser(UserLoginDTO dto) {
        if (dto.login() == null || dto.login().isBlank()) {
            throw new BizException("用户名、邮箱或手机号不能为空！");
        }
        if (dto.password() == null || dto.password().isBlank()) {
            throw new BizException("密码不能为空！");
        }

        Optional<User> optUser = switch (dto.type()) {
            case USERNAME -> Optional.ofNullable(userMapper.selectByUsername(dto.login()).orElse(null));
            case EMAIL -> Optional.ofNullable(userMapper.selectByEmail(dto.login()).orElse(null));
            case PHONE_NUMBER -> Optional.ofNullable(userMapper.selectByPhoneNumber(dto.login()).orElse(null));
        };

        User user = optUser.orElseThrow(() -> new BizException("用户不存在！"));

        if (user.getAccountStatus() == User.AccountStatus.FROZEN) {
            throw new BizException("账户已冻结！");
        }

        if (!encoder.matches(dto.password(), user.getPasswordHash())) {
            handleLoginFail(user);
            throw new BizException("密码错误！");
        }

        user.setLoginFailCount(0);
        user.setLoginLockedUntil(null);
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIP(dto.ip());
        userMapper.updateLoginInfo(user);
        log.info("用户登录成功: {}", user.getUserId());
        return user;
    }

    private void handleLoginFail(User user) {
        int fails = user.getLoginFailCount() + 1;
        user.setLoginFailCount(fails);
        if (fails >= 3) {
            user.setLoginLockedUntil(LocalDateTime.now().plusMinutes(5));
        }
        userMapper.updateLoginInfo(user);
    }
}
