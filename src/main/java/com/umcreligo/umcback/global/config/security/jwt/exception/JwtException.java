package com.umcreligo.umcback.global.config.security.jwt.exception;

import lombok.Getter;

@Getter
public class JwtException extends RuntimeException {

    private final JwtErrorCode jwtErrorCode;
    private final int code;
    private final String errorMessage;

    // Without Cause Exception
    public JwtException(JwtErrorCode errorcode) {
        super(errorcode.getErrorMessage());
        this.jwtErrorCode = errorcode;
        this.code = errorcode.getCode();
        this.errorMessage = errorcode.getErrorMessage();
    }

    public JwtException(JwtErrorCode errorcode, String errorMessage) {
        super(errorMessage);
        this.jwtErrorCode = errorcode;
        this.code = errorcode.getCode();
        this.errorMessage = errorMessage;
    }

    // With Cause Exception
    public JwtException(JwtErrorCode errorcode, Exception cause) {
        super(errorcode.getErrorMessage(), cause);
        this.jwtErrorCode = errorcode;
        this.code = errorcode.getCode();
        this.errorMessage = errorcode.getErrorMessage();
    }

    public JwtException(JwtErrorCode errorcode, String errorMessage, Exception cause) {
        super(errorMessage, cause);
        this.jwtErrorCode = errorcode;
        this.code = errorcode.getCode();
        this.errorMessage = errorMessage;
    }
}