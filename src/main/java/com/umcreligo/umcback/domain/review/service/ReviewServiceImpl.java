package com.umcreligo.umcback.domain.review.service;

import com.umcreligo.umcback.domain.church.domain.Church;
import com.umcreligo.umcback.domain.church.repository.ChurchRepository;
import com.umcreligo.umcback.domain.review.domain.Review;
import com.umcreligo.umcback.domain.review.dto.CreateReviewParam;
import com.umcreligo.umcback.domain.review.dto.UpdateReviewParam;
import com.umcreligo.umcback.domain.review.repository.ReviewRepository;
import com.umcreligo.umcback.domain.user.domain.User;
import com.umcreligo.umcback.domain.user.repository.UserRepository;
import com.umcreligo.umcback.global.config.BaseException;
import com.umcreligo.umcback.global.config.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Transactional
@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ChurchRepository churchRepository;

    @Override
    public Long createReview(CreateReviewParam param) {
        User user = this.userRepository.findWithJoinByIdAndStatus(param.getUserId(), User.UserStatus.ACTIVE).orElse(null);
        Church church = this.churchRepository.findWithJoinByIdAndStatus(param.getChurchId(), Church.ChurchStatus.ACTIVE).orElse(null);

        if (user == null) {
            throw new BaseException(BaseResponseStatus.INVALID_USER_ID);
        }

        if (church == null) {
            throw new BaseException(BaseResponseStatus.INVALID_CHURCH_ID);
        }

        if (this.reviewRepository.existsByUserIdAndChurchIdAndStatus(user.getId(), church.getId(), Review.ReviewStatus.ACTIVE)) {
            throw new BaseException(BaseResponseStatus.ALREADY_REVIEWED_CHURCH);
        }

        Review review = Review.builder()
            .user(user)
            .church(church)
            .title(param.getTitle())
            .text(param.getText())
            .anonymous(param.getAnonymous())
            .status(Review.ReviewStatus.ACTIVE)
            .build();

        this.reviewRepository.saveAndFlush(review);
        return review.getId();
    }

    @Override
    public void updateReview(UpdateReviewParam param) {
        Review review = this.reviewRepository.findWithJoinByIdAndStatus(param.getId(), Review.ReviewStatus.ACTIVE).orElse(null);

        if (review == null) {
            throw new BaseException(BaseResponseStatus.NOT_FOUND);
        }

        if (param.getUserId() == null || !Objects.equals(review.getUser().getId(), param.getUserId())) {
            throw new BaseException(BaseResponseStatus.FORBIDDEN);
        }

        review.update(param.getTitle(), param.getText(), param.getAnonymous());
    }

    @Override
    public void deleteReview(Long userId, Long reviewId) {
        Review review = this.reviewRepository.findWithJoinByIdAndStatus(reviewId, Review.ReviewStatus.ACTIVE).orElse(null);

        if (review == null) {
            return;
        }

        if (userId == null || !Objects.equals(review.getUser().getId(), userId)) {
            throw new BaseException(BaseResponseStatus.FORBIDDEN);
        }

        review.delete();
    }
}
