package com.umcreligo.umcback.global.config.security.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umcreligo.umcback.global.config.security.jwt.exception.ErrorResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.umcreligo.umcback.global.config.security.jwt.exception.JwtErrorCode.LOGIN_FAILED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
//로그인 실패시 실행되는 클래스
public class UserFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ErrorResponse errorResponse = new ErrorResponse(LOGIN_FAILED);
        response.setStatus(LOGIN_FAILED.getStatusCode());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        new ObjectMapper().writeValue(response.getWriter(), errorResponse);
    }
}
