package com.umcreligo.umcback.global.config.security.jwt.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final boolean isSuccess;
    private final int code;
    private final String message;

    public ErrorResponse(JwtException e) {
        this.isSuccess = false;
        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public ErrorResponse(JwtErrorCode errorCode) {
        this.isSuccess = false;
        this.code = errorCode.getCode();
        this.message = errorCode.getErrorMessage();
    }

    public boolean getIsSuccess() {
        return this.isSuccess;
    }
}
