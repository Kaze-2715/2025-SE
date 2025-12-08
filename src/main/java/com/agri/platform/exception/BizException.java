package com.agri.platform.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BizException extends RuntimeException {
    private final HttpStatus status;

    public BizException(String message) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public BizException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public BizException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public static BizException badRequest(String message) {
        return new BizException(HttpStatus.BAD_REQUEST, message);
    }
}
