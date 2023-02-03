package com.umcreligo.umcback.domain.review.dto;

import com.umcreligo.umcback.global.config.validation.NullOrNotBlank;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UpdateReviewRequest {
    @NullOrNotBlank
    @Size(max = 100, message = "size must be between 0 and 300")
    private String title;

    @NullOrNotBlank
    @Size(max = 1000, message = "size must be between 0 and 1000")
    private String text;

    private Boolean anonymous;
}
