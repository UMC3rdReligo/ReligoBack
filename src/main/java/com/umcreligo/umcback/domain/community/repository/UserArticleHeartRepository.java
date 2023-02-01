package com.umcreligo.umcback.domain.community.repository;

import com.umcreligo.umcback.domain.community.domain.Article;
import com.umcreligo.umcback.domain.community.domain.UserArticleHeart;
import com.umcreligo.umcback.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserArticleHeartRepository extends JpaRepository<UserArticleHeart, Long> {
    public boolean existsByArticleAndUser(User user, Article article);

    public void deleteUserArticleHeartByArticleAndUser(User user, Article article);
}
