package com.umcreligo.umcback.global.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    SUCCESS(true, HttpStatus.OK, 200, "요청에 성공하였습니다."),
    BAD_REQUEST(false, HttpStatus.BAD_REQUEST, 400, "입력값을 확인해주세요."),
    UNAUTHORIZED(false, HttpStatus.UNAUTHORIZED, 401, "인증이 필요합니다."),
    FORBIDDEN(false, HttpStatus.FORBIDDEN, 403, "권한이 없습니다."),
    NOT_FOUND(false, HttpStatus.NOT_FOUND, 404, "대상을 찾을 수 없습니다."),
    INVALID_USER_ID(false, HttpStatus.BAD_REQUEST, 10001, "유저를 찾을 수 없습니다."),
    INVALID_CHURCH_ID(false, HttpStatus.BAD_REQUEST, 10002, "교회를 찾을 수 없습니다."),
    ALREADY_HAS_CHURCH(false, HttpStatus.CONFLICT, 10003, "이미 가입된 교회가 있습니다."),
    ALREADY_JOINED_CHURCH(false, HttpStatus.CONFLICT, 10004, "이미 가입한 교회입니다."),
    NICKNAME_DUPLICATE(false, HttpStatus.CONFLICT, 10005, "이미 존재하는 닉네임입니다."),
    EMPTY_USER_NICKNAME(false, HttpStatus.BAD_REQUEST, 10006, "값이 비워져 있습니다."),
    INVALID_NICKNAME(false, HttpStatus.BAD_REQUEST, 10007, "닉네임의 형식을 확인해주세요"),
    INVALID_PLATFORM_CODE(false, HttpStatus.BAD_REQUEST, 10008, "잘못된 교단 코드입니다."),
    INVALID_LOCATION_CODE(false, HttpStatus.BAD_REQUEST, 10009, "잘못된 지역 코드입니다."),
    INVALID_HASH_TAG(false, HttpStatus.BAD_REQUEST, 10010, "잘못된 해시태그입니다."),
    ALREADY_REVIEWED_CHURCH(false, HttpStatus.CONFLICT, 10011, "이미 리뷰를 작성한 교회입니다.");

    private final boolean isSuccess;
    @JsonIgnore
    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, HttpStatus httpStatus, int code, String message) {
        this.isSuccess = isSuccess;
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
