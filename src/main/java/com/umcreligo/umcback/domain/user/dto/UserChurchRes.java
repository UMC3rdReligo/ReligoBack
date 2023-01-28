package com.umcreligo.umcback.domain.user.dto;

import com.umcreligo.umcback.domain.church.domain.Church;
import com.umcreligo.umcback.domain.church.dto.FindChurchResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class UserChurchRes {
    private String name;
    private String nickname;
    private Map<String,Object> info;
    private List<String> hashTags;
    private String mainImage;
    private List<String> detailImages;
}
