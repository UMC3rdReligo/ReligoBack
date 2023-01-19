package com.umcreligo.umcback.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginTokenRes {
    private String accessToken;
    private String refreshToken;

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
