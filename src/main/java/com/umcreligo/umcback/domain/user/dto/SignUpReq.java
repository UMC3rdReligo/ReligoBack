package com.umcreligo.umcback.domain.user.dto;

import com.umcreligo.umcback.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpReq {
    private String email;
    private String password;
    private String PhoneNumber;
    private String gender;

    public User createUser(String encodedPassword){
        return User.builder()
                .email(this.email)
                .password(encodedPassword)
                .phoneNum(this.PhoneNumber)
                .gender(this.gender)
                .build();
    }

}
