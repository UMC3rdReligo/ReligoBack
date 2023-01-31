package com.umcreligo.umcback.domain.community.repository;

import com.umcreligo.umcback.domain.community.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserArticleHeartRepository extends JpaRepository<Article, Long> {

}
