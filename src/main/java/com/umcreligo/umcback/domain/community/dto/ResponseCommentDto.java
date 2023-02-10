package com.umcreligo.umcback.domain.community.dto;

import lombok.Data;

@Data
public class ResponseCommentDto {
    private String name;
    private String text;
    private String createdAt;

}
