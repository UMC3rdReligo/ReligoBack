package com.umcreligo.umcback.global.config.security.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umcreligo.umcback.domain.user.domain.User;
import com.umcreligo.umcback.domain.user.dto.LoginTokenRes;
import com.umcreligo.umcback.domain.user.repository.UserRepository;
import com.umcreligo.umcback.global.config.BaseResponse;
import com.umcreligo.umcback.global.config.security.jwt.JwtService;
import com.umcreligo.umcback.global.config.security.userdetails.PrincipalUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Component
//로그인 성공시 처리 클래스
public class UserSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PrincipalUserDetails userDetails = (PrincipalUserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("가입된 이메일이 존재하지 않습니다."));

        String signYN;
        //Auth 로그인 시 가입이 되어있는지 체크
        if(user.getName()== null){
            signYN="가입이 안되어 있습니다.";
        }else{
            signYN="가입이 되어 있습니다.";
        }
        // JWT Token 생성 & Response
        String accessToken = jwtService.createAccessToken(user.getEmail(), user.getId());
        String refreshToken = jwtService.createRefreshToken(user.getEmail());
        user.updateRefreshToken(refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        LoginTokenRes loginTokenRes = new LoginTokenRes(accessToken,refreshToken,signYN);
        new ObjectMapper().writeValue(response.getWriter(), new BaseResponse(loginTokenRes));
    }
}
