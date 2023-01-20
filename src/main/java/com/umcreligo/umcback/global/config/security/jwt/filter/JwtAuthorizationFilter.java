package com.umcreligo.umcback.global.config.security.jwt.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umcreligo.umcback.global.config.security.jwt.JwtService;
import com.umcreligo.umcback.global.config.security.jwt.exception.ErrorResponse;
import com.umcreligo.umcback.global.config.security.jwt.exception.JwtErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.umcreligo.umcback.global.config.security.jwt.JwtService.TOKEN_HEADER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter { //Jwt올바른지 체크

    private final JwtService jwtService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) { //이 필터 안걸치는 path
        String path = request.getServletPath();
        request.getMethod();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return (
                // TODO 인증이 필요없는 로직 추가
                pathMatcher.match("/user/login", path) || pathMatcher.match("/user/signup", path) && request.getMethod().equals("POST")
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        System.out.println("ㅇ이ㄴ증");
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        JwtErrorCode errorCode = null;

        // Header에 토큰이 존재하지 않을 시
        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_HEADER_PREFIX)) {
            errorCode = JwtErrorCode.TOKEN_NOT_EXIST;
        } else {
            try {
                String accessToken = authorizationHeader.substring(TOKEN_HEADER_PREFIX.length());
                DecodedJWT decodedJWT = jwtService.verifyToken(accessToken);

                String role = decodedJWT.getClaim("role").asString();
                List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
                String email = decodedJWT.getSubject();

                // SecurityContextHolder에 accessToken 포함하여 저장
                Authentication authToken = new UsernamePasswordAuthenticationToken(email, accessToken, authorities);
                SecurityContextHolder.getContext().setAuthentication(authToken);
                filterChain.doFilter(request, response);
            } catch (TokenExpiredException e) {
                // Access Token 만료
                errorCode = JwtErrorCode.ACCESS_TOKEN_EXPIRED;
            } catch (Exception e) {
                // 유효하지 않은 Access Token
                errorCode = JwtErrorCode.INVALID_TOKEN;
            }
        }

        if (errorCode != null) {
            response.setStatus(errorCode.getStatusCode());
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            ErrorResponse errorResponse = new ErrorResponse(errorCode);
            new ObjectMapper().writeValue(response.getWriter(), errorResponse);
        }
    }
}
