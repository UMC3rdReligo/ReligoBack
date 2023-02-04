package com.umcreligo.umcback.global.config.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NaverProfile {
    private String resultCode;
    private String message;
    private Map<String, Object> response;
}
