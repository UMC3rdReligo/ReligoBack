package com.umcreligo.umcback.global.config.security.jwt.exception;

import com.umcreligo.umcback.global.config.BaseException;
import com.umcreligo.umcback.global.config.BaseResponse;
import com.umcreligo.umcback.global.config.BaseResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        List<String> errors = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> (error.getField() + " " +
                Objects.requireNonNullElse(error.getDefaultMessage(), "")).trim() + ".")
            .collect(Collectors.toList());

        JwtException customException = new JwtException(INVALID_VALUE, StringUtils.join(errors, " "));
        return ResponseEntity
            .status(INVALID_VALUE.getHttpStatus())
            .body(new ErrorResponse(customException));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(new BaseResponse<>(BaseResponseStatus.BAD_REQUEST));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<BaseResponse<?>> handleMissingServletRequestParameterException(
        MissingServletRequestParameterException e) {
        return ResponseEntity.badRequest().body(new BaseResponse<>(BaseResponseStatus.BAD_REQUEST));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<BaseResponse<?>> handleBindException(BindException e) {
        return ResponseEntity.badRequest().body(new BaseResponse<>(BaseResponseStatus.BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse<?>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.badRequest().body(new BaseResponse<>(BaseResponseStatus.BAD_REQUEST));
    }

    // Application Exception
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<?>> handleBaseException(BaseException e) {
        return ResponseEntity
            .status(e.getStatus().getHttpStatus())
            .body(new BaseResponse<>(e.getStatus()));
    }

    // 이외 Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        log.error("[Common Exception] url: {} | errorMessage: {}",
            request.getRequestURL(), e.getMessage());
        e.printStackTrace();
        return ResponseEntity
            .status(SERVER_INTERNAL_ERROR.getHttpStatus())
            .body(new ErrorResponse(SERVER_INTERNAL_ERROR));
    }
}
