package com.umcreligo.umcback.domain.community.service;

import com.umcreligo.umcback.domain.church.repository.ChurchRepository;
import com.umcreligo.umcback.domain.community.domain.Article;
import com.umcreligo.umcback.domain.community.domain.Comment;
import com.umcreligo.umcback.domain.community.domain.CommunityType;
import com.umcreligo.umcback.domain.community.dto.FindArticleRes;
import com.umcreligo.umcback.domain.community.dto.SaveArticleReq;
import com.umcreligo.umcback.domain.community.repository.ArticleRepository;
import com.umcreligo.umcback.domain.community.repository.CommentRepository;
import com.umcreligo.umcback.domain.community.repository.UserArticleHeartRepository;
import com.umcreligo.umcback.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    private final UserRepository userRepository;

    private final ChurchRepository churchRepository;

    //input : 커뮤니티 타입
    //return :  타입에 따른 커뮤니티 정보 목록
    public List<FindArticleRes> findCommunities(CommunityType type){
        int FINDARTICLE_COUNT = 20; // 반환할 개수
        List<Article> allArticle = articleRepository.findArticleByTypeOrderByCreatedAtDesc(type);
        List<FindArticleRes> resultList = new ArrayList<>();

        if(allArticle.size() < 20){
            FINDARTICLE_COUNT = allArticle.size();
        }

        for(int i = 0 ; i < FINDARTICLE_COUNT ; i++){
            Article article = allArticle.get(i);
            List<Comment> commentList = commentRepository.findCommentByArticle(article.getId());
            int heartCnt = article.getHeartCount();
            FindArticleRes findArticle = new FindArticleRes();
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

        //교회 게시글이 아닌 경우 -1을 받고, 데이터베이스에 null값 저장
        if(saveArticleReq.getChurchId() == -1) article.setChurch(null);
        else article.setChurch(churchRepository.findById(saveArticleReq.getChurchId()).get());

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
    public FindArticleRes test2(){
        return new FindArticleRes();
    }
}
