package com.agri.platform.service.user;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import com.agri.platform.DTO.UserLoginDTO;
import com.agri.platform.entity.user.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agri.platform.exception.BizException;
import com.agri.platform.mapper.user.UserMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserLoginService {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User loginUser(UserLoginDTO dto, HttpServletRequest request) {
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

        User user = optUser.orElseThrow(() -> new BizException(HttpStatus.BAD_REQUEST,"用户不存在！"));
        
        if (user.getAccountStatus() == User.AccountStatus.FROZEN) {
            throw new BizException(HttpStatus.FORBIDDEN,"账户已冻结！");
        }

        if (!encoder.matches(dto.password(), user.getPasswordHash())) {
            handleLoginFail(user);
            throw new BizException(HttpStatus.UNAUTHORIZED,"密码错误！");
        }

        user.setLoginFailCount(0);
        user.setLoginLockedUntil(null);
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIP(dto.ip());
        userMapper.updateLoginInfo(user);

        List<String> perms = userMapper.listPermCodeByUserId(user.getUserId());
        HttpSession session = request.getSession(true);
        session.setAttribute("userId", user.getUserId());
        session.setAttribute("perms", perms);

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
