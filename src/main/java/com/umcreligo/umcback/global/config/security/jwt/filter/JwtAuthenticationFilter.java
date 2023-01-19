package com.umcreligo.umcback.global.config.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umcreligo.umcback.domain.user.dto.LoginDto;
import com.umcreligo.umcback.global.config.security.userdetails.PrincipalUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private ObjectMapper mapper = new ObjectMapper();

    //로그인 시도시 이 메서드 실행해서 Authentication에 담음
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        System.out.println("----------------------------");
        ServletInputStream inputStream = null;
        LoginDto helloData = null;
        try {
            inputStream = request.getInputStream();
            String msgBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            System.out.println("msg Body = " + msgBody);


            helloData = mapper.readValue(msgBody, LoginDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(helloData.getEmail());
        System.out.println(helloData.getPassword());
        System.out.println("----------------------------");

        String email = helloData.getEmail();
        String password = helloData.getPassword();
        System.out.println(email + " " +password);
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        PrincipalUserDetails principalDetailis = (PrincipalUserDetails) authentication.getPrincipal();
        System.out.println("Authentication : "+principalDetailis.getUser().getEmail());
        return authentication;
    }
}
