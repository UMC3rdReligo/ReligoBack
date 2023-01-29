package com.umcreligo.umcback.global.config.security.jwt.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.umcreligo.umcback.global.config.security.jwt.exception.JwtErrorCode.*;

@Slf4j
@RestControllerAdvice
public class JwtExceptionHandler {

    // CustomException
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(JwtException e, HttpServletRequest request) {
        log.error("[CustomException] url: {} | errorType: {} | errorMessage: {} | cause Exception: ",
            request.getRequestURL(), e.getJwtErrorCode(), e.getMessage(), e.getCause());

        return ResponseEntity
            .status(e.getJwtErrorCode().getHttpStatus())
            .body(new ErrorResponse(e));
    }

    // Not Support Http Method Exception
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMethodException(
        HttpRequestMethodNotSupportedException e,
        HttpServletRequest request
    ) {
        log.error("[HttpRequestMethodNotSupportedException] " +
                "url: {} | errorType: {} | errorMessage: {} | cause Exception: ",
            request.getRequestURL(), INVALID_HTTP_METHOD, INVALID_HTTP_METHOD.getErrorMessage(), e);

        return ResponseEntity
            .status(INVALID_HTTP_METHOD.getHttpStatus())
            .body(new ErrorResponse(INVALID_HTTP_METHOD));
    }

    // Validation Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
        MethodArgumentNotValidException e,
        HttpServletRequest request
    ) {
        String validationMessage = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        log.error("[MethodArgumentNotValidException] url: {} | errorType: {} | errorMessage: {} | cause Exception: ",
            request.getRequestURL(), INVALID_VALUE, validationMessage, e);

        JwtException customException = new JwtException(INVALID_VALUE, validationMessage);
        return ResponseEntity
            .status(INVALID_VALUE.getHttpStatus())
            .body(new ErrorResponse(customException));
    }

    // 이외 Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e, HttpServletRequest request) {
        log.error("[Common Exception] url: {} | errorMessage: {}",
            request.getRequestURL(), e.getMessage());
        e.printStackTrace();
        return ResponseEntity
            .status(SERVER_INTERNAL_ERROR.getHttpStatus())
            .body(new ErrorResponse(SERVER_INTERNAL_ERROR));
    }
}
