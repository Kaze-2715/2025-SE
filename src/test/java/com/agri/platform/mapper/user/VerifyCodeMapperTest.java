package com.agri.platform.mapper.user;

import com.agri.platform.entity.user.VerifyCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class VerifyCodeMapperTest {

    @Autowired
    private VerifyCodeMapper verifyCodeMapper;

    @Test
    public void testSaveCode() {
        // 准备测试数据
        String bizType = "REGISTER";
        String target = "test@example.com";
        String code = "123456";
        LocalDateTime expireTime = LocalDateTime.now().plusMinutes(10);

        // 执行保存操作
        verifyCodeMapper.saveCode(bizType, target, code, expireTime);

        // 验证保存成功
        VerifyCode savedCode = verifyCodeMapper.findLatestCode(bizType, target);
        assertThat(savedCode).isNotNull();
        assertThat(savedCode.getBizType().name()).isEqualTo(bizType);
        assertThat(savedCode.getTarget()).isEqualTo(target);
        assertThat(savedCode.getCode()).isEqualTo(code);
        assertThat(savedCode.getExpireTime()).isEqualTo(expireTime);
        assertThat(savedCode.getUsed()).isFalse();
    }

    @Test
    public void testFindLatestCode() {
        // 准备测试数据
        String bizType = "LOGIN";
        String target = "user@example.com";
        String code1 = "111111";
        String code2 = "222222";
        LocalDateTime expireTime = LocalDateTime.now().plusMinutes(10);

        // 保存两个验证码
        verifyCodeMapper.saveCode(bizType, target, code1, expireTime.minusMinutes(5));
        verifyCodeMapper.saveCode(bizType, target, code2, expireTime);

        // 验证能找到最新的验证码
        VerifyCode latestCode = verifyCodeMapper.findLatestCode(bizType, target);
        assertThat(latestCode).isNotNull();
        assertThat(latestCode.getCode()).isEqualTo(code2);
    }

    @Test
    public void testMarkUsed() {
        // 准备测试数据
        String bizType = "RESET_PASSWORD";
        String target = "reset@example.com";
        String code = "333333";
        LocalDateTime expireTime = LocalDateTime.now().plusMinutes(10);

        // 保存验证码
        verifyCodeMapper.saveCode(bizType, target, code, expireTime);

        // 获取保存的验证码
        VerifyCode savedCode = verifyCodeMapper.findLatestCode(bizType, target);
        assertThat(savedCode.getUsed()).isFalse();

        // 标记为已使用
        verifyCodeMapper.markUsed(savedCode.getId());

        // 验证标记成功
        VerifyCode updatedCode = verifyCodeMapper.findLatestCode(bizType, target);
        assertThat(updatedCode).isNull(); // 因为findLatestCode只查找未使用的验证码
    }

    @Test
    public void testDeleteExpiredCodes() {
        // 准备测试数据
        String bizType = "REGISTER";
        String target1 = "expired1@example.com";
        String target2 = "expired2@example.com";
        String code = "444444";

        // 保存一个过期的验证码和一个未过期的验证码
        LocalDateTime pastTime = LocalDateTime.now().minusMinutes(10);
        LocalDateTime futureTime = LocalDateTime.now().plusMinutes(10);

        verifyCodeMapper.saveCode(bizType, target1, code, pastTime);
        verifyCodeMapper.saveCode(bizType, target2, code, futureTime);

        // 删除过期验证码
        int deletedCount = verifyCodeMapper.deleteExpiredCodes(LocalDateTime.now());

        // 验证删除数量
        assertThat(deletedCount).isEqualTo(1);

        // 验证过期的验证码已被删除，未过期的还在
        VerifyCode expiredCode = verifyCodeMapper.findLatestCode(bizType, target1);
        VerifyCode validCode = verifyCodeMapper.findLatestCode(bizType, target2);

        assertThat(expiredCode).isNull();
        assertThat(validCode).isNotNull();
    }
}