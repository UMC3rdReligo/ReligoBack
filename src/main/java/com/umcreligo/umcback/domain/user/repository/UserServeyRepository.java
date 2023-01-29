package com.umcreligo.umcback.domain.user.repository;

import com.umcreligo.umcback.domain.user.domain.UserServey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserServeyRepository extends JpaRepository<UserServey, Long> {
    List<UserServey> findAllByUserIdOrderByIdDesc(Long userId);
}
