package com.umcreligo.umcback.global.config.security.jwt.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final int code;
    private final String errorMessage;

    public ErrorResponse(JwtException e) {
        this.code = e.getCode();
        this.errorMessage = e.getMessage();
    }

    public ErrorResponse(JwtErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.errorMessage = errorCode.getErrorMessage();
    }
}
