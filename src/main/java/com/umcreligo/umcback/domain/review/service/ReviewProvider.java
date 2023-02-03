package com.umcreligo.umcback.domain.review.service;

import com.umcreligo.umcback.domain.review.dto.FindReviewResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewProvider {
    Optional<FindReviewResult> findReview(Long requestedByUserId, Long reviewId);

    Page<FindReviewResult> findChurchReviews(Long requestedByUserId, Long churchId, Pageable pageable);

    Page<FindReviewResult> findMyReviews(Long requestedByUserId, Pageable pageable);
}
