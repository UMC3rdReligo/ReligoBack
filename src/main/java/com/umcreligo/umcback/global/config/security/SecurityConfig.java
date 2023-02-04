package com.umcreligo.umcback.global.config.security;

import com.umcreligo.umcback.domain.user.repository.UserRepository;
import com.umcreligo.umcback.global.config.security.jwt.KakaoOAuthService;
import com.umcreligo.umcback.global.config.security.jwt.NaverOAuthService;
import com.umcreligo.umcback.global.config.security.jwt.filter.JwtAuthenticationFilter;
import com.umcreligo.umcback.global.config.security.jwt.filter.JwtAuthorizationFilter;
import com.umcreligo.umcback.global.config.security.jwt.filter.KakaoAuthenticationFilter;
import com.umcreligo.umcback.global.config.security.jwt.filter.NaverAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.persistence.EntityManagerFactory;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthorizationFilter authorizationFilter;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final AuthenticationManagerBuilder authManagerBuilder;
    private final KakaoOAuthService kakaoOAuthService;

    private final NaverOAuthService naverOAuthService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers( "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/webjars/**");
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationFilter authenticationFilter
                = new JwtAuthenticationFilter(authManagerBuilder.getOrBuild(),userRepository,passwordEncoder,entityManagerFactory);
        // 로그인 인증 필터
        authenticationFilter.setFilterProcessesUrl("/user/login");
        authenticationFilter.setAuthenticationSuccessHandler(successHandler);
        authenticationFilter.setAuthenticationFailureHandler(failureHandler);

        KakaoAuthenticationFilter customKakaoAuthenticationFilter
            = new KakaoAuthenticationFilter(kakaoOAuthService, userRepository, authManagerBuilder.getOrBuild(),passwordEncoder,entityManagerFactory);
        customKakaoAuthenticationFilter.setFilterProcessesUrl("/user/kakao");
        customKakaoAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        customKakaoAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);

        NaverAuthenticationFilter customNaverAuthenticationFilter
            = new NaverAuthenticationFilter(naverOAuthService, userRepository, authManagerBuilder.getOrBuild(),passwordEncoder,entityManagerFactory);
        customNaverAuthenticationFilter.setFilterProcessesUrl("/user/naver");
        customNaverAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        customNaverAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);



        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(STATELESS) // Using JWT
                .and()
                .addFilter(authenticationFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .authorizeRequests()
                .anyRequest().permitAll();

        return http.build();
    }
}
