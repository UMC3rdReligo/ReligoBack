package com.umcreligo.umcback.domain.user.repository;

import com.umcreligo.umcback.domain.user.domain.User;
import com.umcreligo.umcback.domain.user.domain.UserServey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserServeyRepository extends JpaRepository<UserServey, Long> {
}
