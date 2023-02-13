package com.umcreligo.umcback.domain.community.repository;

import com.umcreligo.umcback.domain.community.domain.Article;
import com.umcreligo.umcback.domain.community.domain.CommunityType;
import com.umcreligo.umcback.domain.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    //타입에 따라 게시글 오름차순 가져오기
    //    CHURCH = 1
    //    PLATFORM = 2
    //    TOTAL = 3
    public List<Article> findArticleByTypeOrderByCreatedAtDesc(CommunityType type);
    Page<Article> findArticleByTypeOrderByCreatedAtDesc(CommunityType type, Pageable pageable);

    public List<Article> findArticleByTypeAndChurchIdOrderByCreatedAtDesc(CommunityType type, Long id);
    Page<Article> findArticleByTypeAndChurchIdOrderByCreatedAtDesc(CommunityType type,Long id, Pageable pageable);


}
