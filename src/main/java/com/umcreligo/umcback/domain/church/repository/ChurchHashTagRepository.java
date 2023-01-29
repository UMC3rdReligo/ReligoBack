package com.umcreligo.umcback.domain.church.repository;

import com.umcreligo.umcback.domain.church.domain.ChurchHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChurchHashTagRepository extends JpaRepository<ChurchHashTag, Long> {
    List<ChurchHashTag> findAllByChurchId(Long churchId);
}
