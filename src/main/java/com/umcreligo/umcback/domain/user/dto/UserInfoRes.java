package com.umcreligo.umcback.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoRes {
    private String name;
    private String nickname;
    private String email;
    private String phoneNum;
    private String platform;
    private String address;
    private String locationCode;
    private String userAddress1;
    private String userAddress2;
    private String userAddress3;
    private Long churchId;
    private String churchName;
    private String churchAddress;
}
