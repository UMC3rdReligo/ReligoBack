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
    private String question_1;
    private String address;
    private String question_2;
    private String question_3;
    //해시태그
    private String question_4;
    private String question_5;
    private String question_6;
    private String question_7;
    private String question_8;
    private String question_9;
    private String question_10;
    private String nickname;

}
