package com.umcreligo.umcback.domain.community.service;

import com.umcreligo.umcback.domain.community.domain.Article;
import com.umcreligo.umcback.domain.community.domain.Comment;
import com.umcreligo.umcback.domain.community.domain.CommunityType;
import com.umcreligo.umcback.domain.community.dto.FindArticle;
import com.umcreligo.umcback.domain.community.repository.ArticleRepository;
import com.umcreligo.umcback.domain.community.repository.CommentRepository;
import com.umcreligo.umcback.domain.community.repository.UserArticleHeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UserArticleHeartRepository userArticleHeartRepository;

    //input : 커뮤니티 타입
    //return :  타입에 따른 커뮤니티 정보 목록
    public List<FindArticle> findCommunities(CommunityType type){
        int FINDARTICLE_COUNT = 20; // 반환할 개수
        List<Article> allArticle = articleRepository.findArticleByTypeOrderByCreatedAtDesc(type);
        List<FindArticle> resultList = new ArrayList<>();

        if(allArticle.size() < 20){
            FINDARTICLE_COUNT = allArticle.size();
        }

        for(int i = 0 ; i < FINDARTICLE_COUNT ; i++){
            Article article = allArticle.get(i);
            List<Comment> commentList = commentRepository.findCommentByArticle(article.getId());
            int heartCnt = article.getHeartCount();
            FindArticle findArticle = new FindArticle(article,heartCnt,commentList);
            resultList.add(findArticle);
        }
        return resultList;
    }

    //글쓰기
    public void saveArticle(){

    }

}
