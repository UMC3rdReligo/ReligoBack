package com.umcreligo.umcback.domain.review.dto;

import com.umcreligo.umcback.global.config.dto.PageResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FindReviewsResponse extends PageResponse {
    List<FindReviewResult> reviews;
}
