package com.umcreligo.umcback.domain.community.repository;

import com.umcreligo.umcback.domain.community.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
