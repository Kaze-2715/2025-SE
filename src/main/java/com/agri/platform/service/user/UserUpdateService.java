package com.agri.platform.service.user;

import java.util.Optional;
import com.agri.platform.entity.user.User;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agri.platform.mapper.user.UserMapper;
import com.agri.platform.DTO.UserUpdateDTO;
import com.agri.platform.exception.BizException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserUpdateService {
    private final UserMapper userMapper;

    public void updateUser(UserUpdateDTO dto) {
        Optional<User> existingUser = userMapper.selectById(dto.userId());
        if (existingUser.isEmpty()) {
            throw new BizException(HttpStatus.BAD_REQUEST, "用户不存在！");
        }
        userMapper.updateByIdSelective(dto);
    }
}
