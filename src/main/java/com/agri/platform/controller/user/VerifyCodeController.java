package com.agri.platform.controller.user;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import com.agri.platform.enums.UserLoginType;
import com.agri.platform.exception.BizException;

import org.springframework.web.bind.annotation.RequestParam;

import com.agri.platform.service.user.VerifyCodeService;

@RestController
@RequestMapping("/api/verify-code")
@RequiredArgsConstructor
public class VerifyCodeController {
    private final VerifyCodeService verifyCodeService;

    @PostMapping("/send")
    public String sendRegisterCode(@RequestParam String target, @RequestParam UserLoginType type) {
        if (type != UserLoginType.EMAIL) {
            throw BizException.badRequest("仅支持邮箱注册发送验证码");
        }

        verifyCodeService.sendCode("REGISTER", target);
        return "验证码发送成功";
    }
}
