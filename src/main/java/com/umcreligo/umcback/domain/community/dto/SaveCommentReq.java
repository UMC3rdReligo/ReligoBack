package com.umcreligo.umcback.domain.community.dto;

import lombok.Data;


@Data
public class SaveCommentReq {

    private Long articleId;

    private String text;
}
