package com.agri.platform.entity.user;

import java.time.LocalDateTime;

public class LoginLog {
    private long logId;
    private String userId;
    private LocalDateTime loginTime;
    private String loginIp;
    private String userAgent;
    private boolean success;
    private String failReason;

    public LoginLog() {
    }

    public LoginLog(long logId, String userId, LocalDateTime loginTime, String loginIp, String userAgent,
            boolean success, String failReason) {
        this.logId = logId;
        this.userId = userId;
        this.loginTime = loginTime;
        this.loginIp = loginIp;
        this.userAgent = userAgent;
        this.success = success;
        this.failReason = failReason;
    }

    @Override
    public String toString() {
        return "LoginLog [logId=" + logId + ", userId=" + userId + ", loginTime=" + loginTime + ", loginIp=" + loginIp
                + ", userAgent=" + userAgent + ", success=" + success + ", failReason=" + failReason + "]";
    }

    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId = logId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}


