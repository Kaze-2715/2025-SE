package com.agri.platform.entity.user;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public class User {
    public enum AccountStatus {
        ACTIVE,
        PENDING,
        FROZEN
    }

    private String userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String passwordHash;
    private AccountStatus accountStatus;
    private LocalDateTime registrationTime;
    private LocalDateTime lastLoginTime;
    private String lastLoginIP;
    private Integer loginFailCount;
    private LocalDateTime loginLockedUntil;

    public User(String userId, String username, String email, String phoneNumber, String passwordHash,
            AccountStatus accountStatus, LocalDateTime registrationTime, LocalDateTime lastLoginTime,
            String lastLoginIP, Integer loginFailCount, LocalDateTime loginLockedUntil) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.accountStatus = accountStatus;
        this.registrationTime = registrationTime;
        this.lastLoginTime = lastLoginTime;
        this.lastLoginIP = lastLoginIP;
        this.loginFailCount = loginFailCount;
        this.loginLockedUntil = loginLockedUntil;
    }

    public User() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public Integer getLoginFailCount() {
        return loginFailCount;
    }

    public void setLoginFailCount(Integer loginFailCount) {
        this.loginFailCount = loginFailCount;
    }

    public LocalDateTime getLoginLockedUntil() {
        return loginLockedUntil;
    }

    public void setLoginLockedUntil(LocalDateTime loginLockedUntil) {
        this.loginLockedUntil = loginLockedUntil;
    }

}
