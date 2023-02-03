package com.umcreligo.umcback.domain.review.service;

import com.umcreligo.umcback.domain.review.dto.CreateReviewParam;
import com.umcreligo.umcback.domain.review.dto.UpdateReviewParam;

public interface ReviewService {
    Long createReview(CreateReviewParam param);

    void updateReview(UpdateReviewParam param);

    void deleteReview(Long userId, Long reviewId);
}
