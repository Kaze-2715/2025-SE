package com.agri.platform.service.user;

import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.agri.platform.entity.user.VerifyCode;
import com.agri.platform.mapper.user.VerifyCodeMapper;
import com.agri.platform.interfaces.ICodeSender;

@Service
@Slf4j
@RequiredArgsConstructor
public class VerifyCodeService {
    private final VerifyCodeMapper codeMapper;
    // private final Map<String, ICodeSender> codeSenders;
    private final ICodeSender emailCodeSender;

    public void sendCode(String bizType, String target) {
        String code = RandomStringUtils.insecure().nextNumeric(6);
        LocalDateTime expireTime = LocalDateTime.now().plusMinutes(10);
        codeMapper.saveCode(bizType, target, code, expireTime);
        // ICodeSender sender = codeSenders.get(target.contains("@") ? "emailCodeSender" : "smsCodeSender");
        emailCodeSender.send(target, code);
        log.info("Sent verification code to {}: {}", target, code);
    }

    public boolean verify(String target, String bizType, String inputCode) {
        VerifyCode code = codeMapper.findLatestCode(bizType, target);
        if (code == null) {
            return false;
        }
        if (code.getUsed() || LocalDateTime.now().isAfter(code.getExpireTime())) {
            return false;
        }
        boolean ok = code.getCode().equals(inputCode);
        if (ok) {
            codeMapper.markUsed(code.getId());
        }
        return ok;
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void cleanupExpiredCodes() {
        int deleted = codeMapper.deleteExpiredCodes(LocalDateTime.now());
        if (deleted > 0) {
            log.info("Cleaned up {} expired verification codes", deleted);
        }
    }
}
