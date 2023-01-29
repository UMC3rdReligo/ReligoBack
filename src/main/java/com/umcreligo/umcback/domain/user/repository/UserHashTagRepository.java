package com.umcreligo.umcback.domain.user.repository;

import com.umcreligo.umcback.domain.user.domain.UserHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHashTagRepository extends JpaRepository<UserHashTag, Long> {
    List<UserHashTag> findAllByUserIdOrderByIdDesc(Long userId);
}
