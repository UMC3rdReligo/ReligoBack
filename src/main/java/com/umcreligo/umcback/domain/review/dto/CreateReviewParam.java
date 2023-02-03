package com.umcreligo.umcback.domain.review.dto;

import lombok.Data;

@Data
public class CreateReviewParam {
    private Long userId;
    private Long churchId;
    private String title;
    private String text;
    private Boolean anonymous;
}
