package com.umcreligo.umcback.domain.review.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindReviewResult {
    private Long id;
    private Long userId;
    private String userNickname;
    private Long churchId;
    private String title;
    private String text;
    private Boolean anonymous;
    private LocalDateTime createdAt;
}
