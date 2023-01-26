package com.umcreligo.umcback.global.config.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umcreligo.umcback.domain.user.domain.User;
import com.umcreligo.umcback.domain.user.dto.LoginDto;
import com.umcreligo.umcback.domain.user.repository.UserRepository;
import com.umcreligo.umcback.global.config.security.userdetails.PrincipalUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManagerFactory enf;
    private ObjectMapper mapper = new ObjectMapper();



    //로그인 시도시 이 메서드 실행해서 Authentication에 담음

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        EntityManager em = enf.createEntityManager();

        //Json으로 받기
        ServletInputStream inputStream = null;
        LoginDto helloData = null;
        try {
            inputStream = request.getInputStream();
            String msgBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            helloData = mapper.readValue(msgBody, LoginDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String email = helloData.getEmail();
        String password = helloData.getPassword();
        //첫 로그인 일시 만 저장함
        Optional<User> option = userRepository.findByEmail(email);
        if(!option.isPresent()){
            //디비 저장하기 위한 transaction 만들기
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            System.out.println(email + " " +password);
            String encodedPassword = passwordEncoder.encode(password);
            User user = new User();
            user.setEmail(email);
            user.setPassword(encodedPassword);
            userRepository.save(user);
            transaction.commit();

        }
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        PrincipalUserDetails principalDetailis = (PrincipalUserDetails) authentication.getPrincipal();
        System.out.println("Authentication : "+principalDetailis.getUser().getEmail());
        return authentication;
    }
}
