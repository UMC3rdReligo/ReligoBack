package com.umcreligo.umcback.domain.review.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateReviewRequest {
    @NotNull(message = "must not be null")
    private Long churchId;

    @NotBlank(message = "must not be blank")
    @Size(max = 100, message = "size must be between 0 and 300")
    private String title;

    @NotBlank(message = "must not be blank")
    @Size(max = 1000, message = "size must be between 0 and 1000")
    private String text;

    @NotNull(message = "must not be null")
    private Boolean anonymous;
}
