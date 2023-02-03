package com.umcreligo.umcback.domain.hashtag.repository;

import com.umcreligo.umcback.domain.hashtag.domain.HashTag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag, String> {
    Optional<HashTag> findByText(String text);

    List<HashTag> findAllByOrderByUserCountDesc(Pageable pageable);

    List<HashTag> findAllByCodeInOrderByCodeAsc(List<String> hashTagCodes);
}
