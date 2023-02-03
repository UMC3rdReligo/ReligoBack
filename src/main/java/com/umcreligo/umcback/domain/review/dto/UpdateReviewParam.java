package com.umcreligo.umcback.domain.review.dto;

import lombok.Data;

@Data
public class UpdateReviewParam {
    private Long id;
    private Long userId;
    private String title;
    private String text;
    private Boolean anonymous;
}
