package com.umcreligo.umcback.domain.review;

import com.umcreligo.umcback.domain.review.dto.*;
import com.umcreligo.umcback.domain.review.service.ReviewProvider;
import com.umcreligo.umcback.domain.review.service.ReviewService;
import com.umcreligo.umcback.global.config.BaseResponse;
import com.umcreligo.umcback.global.config.BaseResponseStatus;
import com.umcreligo.umcback.global.config.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.NoSuchElementException;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewProvider reviewProvider;
    private final ReviewService reviewService;
    private final JwtService jwtService;

    @GetMapping("")
    public ResponseEntity<BaseResponse<FindReviewsResponse>> findChurchReviews(@RequestParam(value = "churchId", required = false) Long churchId,
                                                                               @RequestParam(value = "page", defaultValue = "1")
                                                                               @Positive(message = "must be greater than 0")
                                                                               Integer page) {
        Long userId = this.jwtService.getId();
        Page<FindReviewResult> reviews = this.reviewProvider.findChurchReviews(userId, churchId, PageRequest.of(page - 1, 20));
        return this.createFindReviewsResponseEntity(reviews);
    }

    @PostMapping("")
    public ResponseEntity<BaseResponse<Long>> createReview(@Valid @RequestBody CreateReviewRequest request) {
        Long userId = this.jwtService.getId();

        CreateReviewParam param = new CreateReviewParam();
        param.setUserId(userId);
        param.setChurchId(request.getChurchId());
        param.setTitle(request.getTitle());
        param.setText(request.getText());
        param.setAnonymous(request.getAnonymous());

        return ResponseEntity.ok(new BaseResponse<>(this.reviewService.createReview(param)));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<BaseResponse<FindReviewResult>> findReview(@PathVariable("reviewId") Long reviewId) {
        Long userId = this.jwtService.getId();

        try {
            return ResponseEntity.ok(new BaseResponse<>(this.reviewProvider.findReview(userId, reviewId).orElseThrow()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<BaseResponse<Boolean>> updateReview(@PathVariable("reviewId") Long reviewId,
                                                              @Valid @RequestBody UpdateReviewRequest request) {
        Long userId = this.jwtService.getId();

        UpdateReviewParam param = new UpdateReviewParam();
        param.setId(reviewId);
        param.setUserId(userId);
        param.setTitle(request.getTitle());
        param.setText(request.getText());
        param.setAnonymous(request.getAnonymous());

        this.reviewService.updateReview(param);
        return ResponseEntity.ok(new BaseResponse<>(true));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<BaseResponse<Boolean>> deleteReview(@PathVariable("reviewId") Long reviewId) {
        Long userId = this.jwtService.getId();
        this.reviewService.deleteReview(userId, reviewId);
        return ResponseEntity.ok(new BaseResponse<>(true));
    }

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<FindReviewsResponse>> findMyReviews(@RequestParam(value = "churchId", required = false) Long churchId,
                                                                           @RequestParam(value = "page", defaultValue = "1")
                                                                           @Positive(message = "must be greater than 0")
                                                                           Integer page) {
        Long userId = this.jwtService.getId();
        Page<FindReviewResult> reviews = this.reviewProvider.findMyReviews(userId, churchId, PageRequest.of(page - 1, 20));
        return this.createFindReviewsResponseEntity(reviews);
    }

    private ResponseEntity<BaseResponse<FindReviewsResponse>> createFindReviewsResponseEntity(Page<FindReviewResult> reviews) {
        FindReviewsResponse response = new FindReviewsResponse();
        response.setReviews(reviews.getContent());
        response.setPage(reviews.getNumber() + 1);
        response.setSize(reviews.getNumberOfElements());
        response.setLimit(reviews.getSize());
        response.setTotalPage(reviews.getTotalPages());
        response.setTotalSize(reviews.getTotalElements());

        return ResponseEntity.ok(new BaseResponse<>(response));
    }
}
