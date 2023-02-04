package com.umcreligo.umcback.domain.review.repository;

import com.umcreligo.umcback.domain.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @EntityGraph(attributePaths = {"user", "church"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Review> findWithJoinByIdAndStatus(Long reviewId, Review.ReviewStatus reviewStatus);

    @EntityGraph(attributePaths = {"user", "church"}, type = EntityGraph.EntityGraphType.LOAD)
    Page<Review> findAllWithJoinByStatusOrderByIdDesc(Review.ReviewStatus reviewStatus, Pageable pageable);

    @EntityGraph(attributePaths = {"user", "church"}, type = EntityGraph.EntityGraphType.LOAD)
    Page<Review> findAllWithJoinByUserIdAndStatusOrderByIdDesc(Long userId, Review.ReviewStatus reviewStatus, Pageable pageable);

    @EntityGraph(attributePaths = {"user", "church"}, type = EntityGraph.EntityGraphType.LOAD)
    Page<Review> findAllWithJoinByChurchIdAndStatusOrderByIdDesc(Long churchId, Review.ReviewStatus reviewStatus, Pageable pageable);

    @EntityGraph(attributePaths = {"user", "church"}, type = EntityGraph.EntityGraphType.LOAD)
    Page<Review> findAllWithJoinByChurchIdAndUserIdAndStatusOrderByIdDesc(Long churchId, Long userId, Review.ReviewStatus reviewStatus, Pageable pageable);

    boolean existsByUserIdAndChurchIdAndStatus(Long userId, Long churchId, Review.ReviewStatus reviewStatus);
}
