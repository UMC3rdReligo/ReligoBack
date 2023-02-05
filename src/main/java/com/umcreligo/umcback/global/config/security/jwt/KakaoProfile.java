package com.umcreligo.umcback.global.config.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class KakaoProfile {
    private Long id;
    private String connected_at;
    private Map<String, Object> properties;
    private Map<String, Object> kakao_account;
}
