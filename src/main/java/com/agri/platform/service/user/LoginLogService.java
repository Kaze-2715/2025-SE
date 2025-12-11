package com.agri.platform.service.user;

import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.agri.platform.entity.user.LoginLog;
import com.agri.platform.mapper.user.UserLoginMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginLogService {
    private final UserLoginMapper mapper;

    @Async("logExecutor")
    public void save(LoginLog log) {
        LoginLog target = new LoginLog();
        BeanUtils.copyProperties(log, target);
        mapper.insertLog(target);
    }
}
