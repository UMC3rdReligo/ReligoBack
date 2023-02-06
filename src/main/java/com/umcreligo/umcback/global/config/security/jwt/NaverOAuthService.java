package com.umcreligo.umcback.global.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class NaverOAuthService {

    public static final String Naver_PROFILE_URL = "https://openapi.naver.com/v1/nid/me";
    public NaverProfile getKakaoProfileWithAccessToken(String accessToken) {
        try {

            HttpHeaders header = new HttpHeaders();
            header.add(AUTHORIZATION, "Bearer " + accessToken);
            header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            RestTemplate rt = new RestTemplate();
            rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(header);

            ResponseEntity<String> response = rt.exchange(
                Naver_PROFILE_URL,
                HttpMethod.GET,
                request,
                String.class
            );

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getBody(), NaverProfile.class);

        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("SNS 로그인에 실패했습니다.");
        }
    }
}
