package com.umcreligo.umcback.domain.hashtag.repository;

import com.umcreligo.umcback.domain.hashtag.domain.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepository extends JpaRepository<HashTag,Long> {
}