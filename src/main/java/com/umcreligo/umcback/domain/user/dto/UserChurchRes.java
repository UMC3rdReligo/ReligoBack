package com.umcreligo.umcback.domain.user.dto;

import com.umcreligo.umcback.domain.church.domain.Church;
import com.umcreligo.umcback.domain.church.dto.FindChurchResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class UserChurchRes {
    private String name;
    private String nickname;
    private String address;
    private String locationCode;
    private Long churchId;
    private String churchName;
    private String churchAddress;
}
