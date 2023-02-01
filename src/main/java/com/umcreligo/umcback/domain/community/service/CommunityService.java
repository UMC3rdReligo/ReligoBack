package com.umcreligo.umcback.domain.community.service;

import com.umcreligo.umcback.domain.church.repository.ChurchRepository;
import com.umcreligo.umcback.domain.community.domain.Article;
import com.umcreligo.umcback.domain.community.domain.Comment;
import com.umcreligo.umcback.domain.community.domain.CommunityType;
import com.umcreligo.umcback.domain.community.dto.FindArticleRes;
import com.umcreligo.umcback.domain.community.dto.SaveArticleReq;
import com.umcreligo.umcback.domain.community.dto.SaveCommentReq;
import com.umcreligo.umcback.domain.community.repository.ArticleRepository;
import com.umcreligo.umcback.domain.community.repository.CommentRepository;
import com.umcreligo.umcback.domain.community.repository.UserArticleHeartRepository;
import com.umcreligo.umcback.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UserArticleHeartRepository userArticleHeartRepository;

    private final UserRepository userRepository;

    private final ChurchRepository churchRepository;

    private int FINDARTICLE_COUNT = 20;
    //전체 커뮤니티 내용 Type = TOTAL
    public List<FindArticleRes> findAllArticles(){
        List<Article> allArticles = articleRepository.findArticleByTypeOrderByCreatedAtDesc(CommunityType.TOTAL);
        List<FindArticleRes> resultList = new ArrayList<>();

        if(allArticles.size() < 20){
            FINDARTICLE_COUNT = allArticles.size();
        }
        for(int i = 0 ; i < FINDARTICLE_COUNT ; i++){
            FindArticleRes findArticleRes = new FindArticleRes();

            Article article = allArticles.get(i);
            List<Comment> commentList = commentRepository.findCommentByArticleId(article.getId());

            findArticleRes.setText(article.getText());
            findArticleRes.setType(typeToString(article.getType()));
            findArticleRes.setHeartCnt(article.getHeartCount());
            findArticleRes.setArticleId(article.getId());
            findArticleRes.setWriter(article.getUser().getNickname());
            findArticleRes.setTitle(article.getTitle());


            //이름, 작성시간, 내용
            Map<String,Object> commentMap = new HashMap<>();
            for(Comment comment:commentList){
                commentMap.put("name",comment.getUser().getNickname());
                commentMap.put("text",comment.getText());
                commentMap.put("createdAt",comment.getCreatedAt());
            }
            findArticleRes.setComments(commentMap);

            resultList.add(findArticleRes);
        }
            return resultList;
    }
    public List<FindArticleRes> findChurchArticles(Long churchid){
        List<Article> allArticles = articleRepository.findArticleByTypeAndChurchIdOrderByCreatedAtDesc(CommunityType.CHURCH,churchid);
        List<FindArticleRes> resultList = new ArrayList<>();

        if(allArticles.size() < 20){
            FINDARTICLE_COUNT = allArticles.size();
        }

        for(int i = 0 ; i < FINDARTICLE_COUNT ; i++){
            FindArticleRes findArticleRes = new FindArticleRes();

            Article article = allArticles.get(i);
            List<Comment> commentList = commentRepository.findCommentByArticleId(article.getId());

            findArticleRes.setText(article.getText());
            findArticleRes.setType(typeToString(article.getType()));
            findArticleRes.setHeartCnt(article.getHeartCount());
            findArticleRes.setArticleId(article.getId());
            findArticleRes.setWriter(article.getUser().getNickname());
            findArticleRes.setTitle(article.getTitle());

            //이름, 작성시간, 내용
            Map<String,Object> commentMap = new HashMap<>();
            for(Comment comment:commentList){
                commentMap.put("name",comment.getUser().getNickname());
                commentMap.put("text",comment.getText());
                commentMap.put("createdAt",comment.getCreatedAt());
            }
            findArticleRes.setComments(commentMap);

            resultList.add(findArticleRes);
        }
        return resultList;
    }
    public List<FindArticleRes> findPlatformArticles(String plaformCode){
        List<Article> allArticles = articleRepository.findArticleByTypeOrderByCreatedAtDesc(stringToType(plaformCode));
        List<FindArticleRes> resultList = new ArrayList<>();

        if(allArticles.size() < 20){
            FINDARTICLE_COUNT = allArticles.size();
        }
        for(int i = 0 ; i < FINDARTICLE_COUNT ; i++){
            FindArticleRes findArticleRes = new FindArticleRes();

            Article article = allArticles.get(i);
            List<Comment> commentList = commentRepository.findCommentByArticleId(article.getId());

            findArticleRes.setText(article.getText());
            findArticleRes.setType(typeToString(article.getType()));
            findArticleRes.setHeartCnt(article.getHeartCount());
            findArticleRes.setArticleId(article.getId());
            findArticleRes.setWriter(article.getUser().getNickname());
            findArticleRes.setTitle(article.getTitle());


            //이름, 작성시간, 내용
            Map<String,Object> commentMap = new HashMap<>();
            for(Comment comment:commentList){
                commentMap.put("name",comment.getUser().getNickname());
                commentMap.put("text",comment.getText());
                commentMap.put("createdAt",comment.getCreatedAt());
            }
            findArticleRes.setComments(commentMap);

            resultList.add(findArticleRes);
        }
        return resultList;
    }

    //글쓰기
    public void saveArticle(SaveArticleReq saveArticleReq){
        Article article = new Article();
        article.setTitle(saveArticleReq.getTitle());
        article.setType(stringToType(saveArticleReq.getType()));
        article.setUser(userRepository.findByEmail(saveArticleReq.getEmail()).get());

        article.setText(saveArticleReq.getText());

        article.setHeartCount(0);

        //교회 게시글이 아닌 경우 -1을 받고, 데이터베이스에 null값 저장
        if(saveArticleReq.getChurchId() == -1) article.setChurch(null);
        else article.setChurch(churchRepository.findById(saveArticleReq.getChurchId()).get());

        articleRepository.save(article);
    }

    public void saveComment(SaveCommentReq saveCommentReq){
        Comment comment = new Comment();
        comment.setUser(userRepository.findByEmail(saveCommentReq.getEmail()).get());
        comment.setText(saveCommentReq.getText());
        comment.setArticle(articleRepository.findById(saveCommentReq.getArticleId()).get());

        commentRepository.save(comment);
    }

    public CommunityType stringToType(String type){
        if(type.equals("church"))
            return CommunityType.CHURCH;
        else if(type.equals("total"))
            return CommunityType.TOTAL;
        else if(type.equals("PA1"))
            return CommunityType.PA1;
        else if(type.equals("PA2"))
            return CommunityType.PA2;
        else if(type.equals("PA3"))
            return CommunityType.PA3;
        else if(type.equals("PB1"))
            return CommunityType.PB1;
        else if(type.equals("PB2"))
            return CommunityType.PB2;
        else if(type.equals("PB3"))
            return CommunityType.PB3;
        else
            return CommunityType.PC1;
    }

    public String typeToString(CommunityType type){
        if(type == CommunityType.CHURCH)
            return "church";
        else if(type == CommunityType.TOTAL)
            return "total";
        else if(type == CommunityType.PA1)
            return "PA1";
        else if(type == CommunityType.PA2)
            return "PA2";
        else if(type == CommunityType.PA3)
            return "PA3";
        else if(type == CommunityType.PB1)
            return "PB1";
        else if(type == CommunityType.PB2)
            return "PB2";
        else if(type == CommunityType.PB3)
            return "PB3";
        else
            return "PC1";
    }


    //DTO JSON 확인용
    public SaveArticleReq test(){
        return new SaveArticleReq();
    }
    public FindArticleRes test2(){
        return new FindArticleRes();
    }
}
