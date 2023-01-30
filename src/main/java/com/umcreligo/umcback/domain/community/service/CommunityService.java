package com.umcreligo.umcback.domain.community.service;

import com.umcreligo.umcback.domain.community.domain.Article;
import com.umcreligo.umcback.domain.community.domain.Comment;
import com.umcreligo.umcback.domain.community.domain.CommunityType;
import com.umcreligo.umcback.domain.community.dto.FindArticle;
import com.umcreligo.umcback.domain.community.dto.SaveArticleReq;
import com.umcreligo.umcback.domain.community.repository.ArticleRepository;
import com.umcreligo.umcback.domain.community.repository.CommentRepository;
import com.umcreligo.umcback.domain.community.repository.UserArticleHeartRepository;
import com.umcreligo.umcback.domain.user.domain.User;
import com.umcreligo.umcback.domain.user.repository.UserRepository;
import com.umcreligo.umcback.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UserArticleHeartRepository userArticleHeartRepository;

    private final UserRepository userRepository;

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
            FindArticle findArticle = new FindArticle();
            findArticle.setText(article.getText());
            findArticle.setType(typeToInt(article.getType()));
            findArticle.setComments(commentList);
            findArticle.setHeartCnt(article.getHeartCount());
            resultList.add(findArticle);
        }
        return resultList;
    }

    //글쓰기
    public void saveArticle(SaveArticleReq saveArticleReq){
        Article article = new Article();
        article.setTitle(saveArticleReq.getTitle());

        article.setType(intToType(saveArticleReq.getType()));

        article.setUser(userRepository.findByEmail(saveArticleReq.getEmail()).get());

        article.setText(saveArticleReq.getText());

        article.setHeartCount(0);

        articleRepository.save(article);
    }

    public int typeToInt(CommunityType type){
        if(type == CommunityType.CHURCH)
            return 1;
        else if(type == CommunityType.PLATFORM)
            return 2;
        else return 3;
    }

    public CommunityType intToType(int type){
        if(type == 1)
            return CommunityType.CHURCH;
        else if(type == 2)
            return CommunityType.PLATFORM;
        else return CommunityType.TOTAL;
    }

    //DTO JSON 확인용
    public SaveArticleReq test(){
        return new SaveArticleReq();
    }
    public FindArticle test2(){
        return new FindArticle();
    }
}
