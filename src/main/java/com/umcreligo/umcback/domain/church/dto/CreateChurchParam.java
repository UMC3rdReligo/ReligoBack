package com.umcreligo.umcback.domain.church.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateChurchParam {
    private String platformCode;
    private String locationCode;
    private String name;
    private String address;
    private String homepageURL;
    private String introduction;
    private String minister;
    private String schedule;
    private String phoneNum;
    private List<String> hashTags;
    private String mainImage;
    private List<String> detailImages;
}
