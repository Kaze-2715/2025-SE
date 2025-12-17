package com.agri.platform.entity.user;

import java.time.LocalDateTime;

public class VerifyCode {
    public enum BizType {
        REGISTER,
        LOGIN,
        RESET_PASSWORD
    }

    private long id;
    private BizType bizType;
    private String target;
    private String code;
    private LocalDateTime expireTime;
    private Boolean used;
    private LocalDateTime createdTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BizType getBizType() {
        return bizType;
    }

    public void setBizType(BizType bizType) {
        this.bizType = bizType;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public VerifyCode(long id, BizType bizType, String target, String code, LocalDateTime expireTime, Boolean used,
            LocalDateTime createdTime) {
        this.id = id;
        this.bizType = bizType;
        this.target = target;
        this.code = code;
        this.expireTime = expireTime;
        this.used = used;
        this.createdTime = createdTime;
    }

    public VerifyCode() {
    }

}
