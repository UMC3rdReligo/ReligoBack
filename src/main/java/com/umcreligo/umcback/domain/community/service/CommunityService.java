package com.umcreligo.umcback.domain.community.service;

import com.umcreligo.umcback.domain.church.repository.ChurchRepository;
import com.umcreligo.umcback.domain.community.domain.Article;
import com.umcreligo.umcback.domain.community.domain.Comment;
import com.umcreligo.umcback.domain.community.domain.CommunityType;
import com.umcreligo.umcback.domain.community.domain.UserArticleHeart;
import com.umcreligo.umcback.domain.community.dto.*;
import com.umcreligo.umcback.domain.community.repository.ArticleRepository;
import com.umcreligo.umcback.domain.community.repository.CommentRepository;
import com.umcreligo.umcback.domain.community.repository.UserArticleHeartRepository;
import com.umcreligo.umcback.domain.review.domain.Review;
import com.umcreligo.umcback.domain.review.dto.FindReviewResult;
import com.umcreligo.umcback.domain.user.domain.User;
import com.umcreligo.umcback.domain.user.repository.UserRepository;
import com.umcreligo.umcback.global.config.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.apache.http.protocol.ResponseServer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UserArticleHeartRepository userArticleHeartRepository;

    private final UserRepository userRepository;

    private final ChurchRepository churchRepository;
    private final JwtService jwtService;

    private int FINDARTICLE_COUNT = 20;

    //전체 커뮤니티 내용 Type = TOTAL
    public List<FindArticleRes> findAllArticles(FindArticleReq findArticleReq) {
        List<Article> allArticles = articleRepository.findArticleByTypeOrderByCreatedAtDesc(CommunityType.TOTAL);
        return findArticle(allArticles, findArticleReq);
    }

    public List<FindArticleRes> findChurchArticles(FindArticleReq findArticleReq) {
        List<Article> allArticles = articleRepository.findArticleByTypeAndChurchIdOrderByCreatedAtDesc(CommunityType.CHURCH, findArticleReq.getChurchId());
        return findArticle(allArticles, findArticleReq);
    }

    public List<FindArticleRes> findPlatformArticles(FindArticleReq findArticleReq) {
        List<Article> allArticles = articleRepository.findArticleByTypeOrderByCreatedAtDesc(stringToType(findArticleReq.getPlatformCode()));
        return findArticle(allArticles, findArticleReq);
    }

    //글쓰기
    public void saveArticle(SaveArticleReq saveArticleReq) {
        Article article = new Article();
        User user = userRepository.findById(jwtService.getId()).get();
        article.setTitle(saveArticleReq.getTitle());
        article.setType(stringToType(saveArticleReq.getType()));
        article.setUser(user);
        article.setText(saveArticleReq.getText());
        article.setHeartCount(0);
        article.setChurch(user.getChurch());
        articleRepository.save(article);
    }

    //댓글쓰기
    public void saveComment(SaveCommentReq saveCommentReq) {
        Comment comment = new Comment();
        comment.setUser(userRepository.findById(jwtService.getId()).get());
        comment.setText(saveCommentReq.getText());
        comment.setArticle(articleRepository.findById(saveCommentReq.getArticleId()).get());

        commentRepository.save(comment);
    }

    //좋아요 버튼 클릭시
    public void clickHeart(HeartClickReq heartClickReq) {
        Article article = articleRepository.findById(heartClickReq.getArticleId()).get();
        User user = userRepository.findById(jwtService.getId()).get();
        System.out.println(article.getTitle());
        System.out.println(user.getEmail());
        System.out.println(userArticleHeartRepository.existsByArticleAndUser(article, user));
        if (userArticleHeartRepository.existsByArticleAndUser(article, user)) {
            //이미 눌렀으면 취소
            userArticleHeartRepository.deleteUserArticleHeartByArticleAndUser(article, user);
            article.setHeartCount(article.getHeartCount() - 1);
            articleRepository.save(article);
        } else {
            //눌려있지 않다면 누름.
            userArticleHeartRepository.save(UserArticleHeart.builder()
                .article(article)
                .user(user)
                .build());
            article.setHeartCount(article.getHeartCount() + 1);
            articleRepository.save(article);
        }
    }

    public DetailArticleRes getDetailArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).get();
        DetailArticleRes detailArticleRes = new DetailArticleRes();
        List<Comment> commentList = commentRepository.findCommentByArticleId(articleId);

        detailArticleRes.setText(article.getText());
        detailArticleRes.setHearted(userArticleHeartRepository.existsByArticleAndUser(article, userRepository.findById(jwtService.getId()).get()));
        detailArticleRes.setArticleId(articleId);
        detailArticleRes.setTitle(article.getTitle());
        detailArticleRes.setWriter(article.getUser().getNickname());
        detailArticleRes.setCommentCnt(commentList.size());
        detailArticleRes.setCreateAt(article.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        detailArticleRes.setHeartCnt(article.getHeartCount());
        List<ResponseCommentDto> responseCommentDtoList = new ArrayList<>();
        for(Comment c : commentList){
            ResponseCommentDto responseCommentDto = new ResponseCommentDto();
            responseCommentDto.setCreatedAt(c.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            responseCommentDto.setText(c.getText());
            responseCommentDto.setName(c.getUser().getNickname());
            responseCommentDtoList.add(responseCommentDto);
        }
        detailArticleRes.setComments(responseCommentDtoList);
        return detailArticleRes;
    }

    /*------내부사용 함수--------*/

    public List<FindArticleRes> findArticle(List<Article> allArticles, FindArticleReq findArticleReq) {
        List<FindArticleRes> resultList = new ArrayList<>();
        Optional<User> optional = userRepository.findById(jwtService.getId());
        User user = optional.get();

        if (allArticles.size() < 20) {
            FINDARTICLE_COUNT = allArticles.size();
        }
        for (int i = 0; i < FINDARTICLE_COUNT; i++) {
            FindArticleRes findArticleRes = new FindArticleRes();

            Article article = allArticles.get(i);
            List<Comment> commentList = commentRepository.findCommentByArticleId(article.getId());

            findArticleRes.setText(article.getText());
            findArticleRes.setType(typeToString(article.getType()));
            findArticleRes.setHeartCnt(article.getHeartCount());
            findArticleRes.setArticleId(article.getId());
            findArticleRes.setWriter(article.getUser().getNickname());
            findArticleRes.setTitle(article.getTitle());
            findArticleRes.setRecently(compareMinute(LocalDateTime.now(), article.getCreatedAt()));
            findArticleRes.setCreateAt(article.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            findArticleRes.setCommentCnt(commentList.size());
            if (userArticleHeartRepository.existsByArticleAndUser(article, user)) findArticleRes.setHearted(true);
            else findArticleRes.setHearted(false);

            resultList.add(findArticleRes);
        }
        return resultList;
    }

//    public Page<FindArticleRes> findArticlePage(Pageable pageable) {
//        Page<Article> articles;
//
//        articles = articleRepository.findArticleByTypeOrderByCreatedAtDesc(CommunityType.TOTAL,pageable);
//
////        List<FindArticleRes> results = articles.stream()
////            .map(article -> this.createFindReviewResult(requestedByUserId, review))
////            .collect(Collectors.toList());
//
//        return new PageImpl<>(results, pageable, articles.getTotalElements());
//    }


    public CommunityType stringToType(String type) {
        if (type.equals("church"))
            return CommunityType.CHURCH;
        else if (type.equals("total"))
            return CommunityType.TOTAL;
        else if (type.equals("PA1"))
            return CommunityType.PA1;
        else if (type.equals("PA2"))
            return CommunityType.PA2;
        else if (type.equals("PA3"))
            return CommunityType.PA3;
        else if (type.equals("PB1"))
            return CommunityType.PB1;
        else if (type.equals("PB2"))
            return CommunityType.PB2;
        else if (type.equals("PB3"))
            return CommunityType.PB3;
        else
            return CommunityType.PC1;
    }

    public String typeToString(CommunityType type) {
        if (type == CommunityType.CHURCH)
            return "church";
        else if (type == CommunityType.TOTAL)
            return "total";
        else if (type == CommunityType.PA1)
            return "PA1";
        else if (type == CommunityType.PA2)
            return "PA2";
        else if (type == CommunityType.PA3)
            return "PA3";
        else if (type == CommunityType.PB1)
            return "PB1";
        else if (type == CommunityType.PB2)
            return "PB2";
        else if (type == CommunityType.PB3)
            return "PB3";
        else
            return "PC1";
    }

    public boolean compareMinute(LocalDateTime date1, LocalDateTime date2) {
        Duration duration = Duration.between(date2, date1);
        if (duration.toMinutes() < 3) return true;
        else return false;
    }


}
