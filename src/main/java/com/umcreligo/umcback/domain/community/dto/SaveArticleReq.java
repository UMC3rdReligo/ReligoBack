package com.umcreligo.umcback.domain.community.dto;

import lombok.Data;


@Data
public class SaveArticleReq {

    private String type;

    private String title;

    private String text;
}
