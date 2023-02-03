package com.umcreligo.umcback.domain.review.service;

import com.umcreligo.umcback.domain.review.domain.Review;
import com.umcreligo.umcback.domain.review.dto.FindReviewResult;
import com.umcreligo.umcback.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReviewProviderImpl implements ReviewProvider {
    private final ReviewRepository reviewRepository;

    @Override
    public Optional<FindReviewResult> findReview(Long requestedByUserId, Long reviewId) {
        Review review = this.reviewRepository.findWithJoinByIdAndStatus(reviewId, Review.ReviewStatus.ACTIVE).orElse(null);

        if (review == null) {
            return Optional.empty();
        }

        return Optional.of(this.createFindReviewResult(requestedByUserId, review));
    }

    @Override
    public Page<FindReviewResult> findChurchReviews(Long requestedByUserId, Long churchId, Pageable pageable) {
        Page<Review> reviews = this.reviewRepository.findAllWithJoinByChurchIdAndStatusOrderByIdDesc(churchId, Review.ReviewStatus.ACTIVE, pageable);
        List<FindReviewResult> results = reviews.stream()
            .map(review -> this.createFindReviewResult(requestedByUserId, review))
            .collect(Collectors.toList());

        return new PageImpl<>(results, pageable, reviews.getTotalElements());
    }

    @Override
    public Page<FindReviewResult> findMyReviews(Long requestedByUserId, Pageable pageable) {
        Page<Review> reviews = this.reviewRepository.findAllWithJoinByUserIdAndStatusOrderByIdDesc(requestedByUserId, Review.ReviewStatus.ACTIVE, pageable);
        List<FindReviewResult> results = reviews.stream()
            .map(review -> this.createFindReviewResult(requestedByUserId, review))
            .collect(Collectors.toList());

        return new PageImpl<>(results, pageable, reviews.getTotalElements());
    }

    private FindReviewResult createFindReviewResult(Long requestedByUserId, Review review) {
        FindReviewResult result = new FindReviewResult();
        result.setId(review.getId());

        if (!review.getAnonymous() || Objects.equals(review.getUser().getId(), requestedByUserId)) {
            result.setUserId(review.getUser().getId());
            result.setUserNickname(StringUtils.trimToEmpty(review.getUser().getNickname()));
        }

        result.setChurchId(review.getChurch().getId());
        result.setTitle(review.getTitle());
        result.setText(review.getText());
        result.setAnonymous(review.getAnonymous());
        result.setCreatedAt(review.getCreatedAt());
        return result;
    }
}
