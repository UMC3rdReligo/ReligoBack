package com.umcreligo.umcback.domain.community;

import com.umcreligo.umcback.domain.church.repository.PlatformRepository;
import com.umcreligo.umcback.domain.community.dto.*;
import com.umcreligo.umcback.domain.community.service.CommunityService;
import com.umcreligo.umcback.global.config.BaseResponse;
import com.umcreligo.umcback.global.config.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;
    private final PlatformRepository platformRepository;

    //명세서 작성 완
    @GetMapping("/community/article")
    public ResponseEntity<BaseResponse<List<FindArticleRes>>> getArticle(@RequestBody FindArticleReq findArticleReq) {
        try {
            if (findArticleReq.getPlatformCode().equals("total"))
                return ResponseEntity.ok(new BaseResponse<>(this.communityService.findAllArticles(findArticleReq)));
            else if (findArticleReq.getPlatformCode().equals("church"))
                return ResponseEntity.ok(new BaseResponse<>(this.communityService.findChurchArticles(findArticleReq)));
            else
                return ResponseEntity.ok(new BaseResponse<>(this.communityService.findPlatformArticles(findArticleReq)));

        } catch (
            NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }

    @GetMapping("/community/article/all")
//    public ResponseEntity<BaseResponse<List<FindArticleRes>>> getAllArticle(@RequestBody FindArticleReq findArticleReq) {
    public ResponseEntity<BaseResponse<List<FindArticleRes>>> getAllArticle(@RequestParam(value = "email")String email) {

        try {
            FindArticleReq findArticleReq = new FindArticleReq(email,"total",-1l);
            return ResponseEntity.ok(new BaseResponse<>(this.communityService.findAllArticles(findArticleReq)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }

    //명세서 작성 완
    @GetMapping("/community/article/church/{churchId}")
    public ResponseEntity<BaseResponse<List<FindArticleRes>>> getChurchArticle(@PathVariable Long churchId, @RequestParam(value = "email")String email) {
        try {
            FindArticleReq findArticleReq = new FindArticleReq(email,"church",churchId);
            return ResponseEntity.ok(new BaseResponse<>(this.communityService.findChurchArticles(findArticleReq)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }

    //명세서 작성 완
    @GetMapping("/community/article/platform/{platformCode}")
    public ResponseEntity<BaseResponse<List<FindArticleRes>>> getPlatformArticle(@PathVariable String platformCode, @RequestParam(value = "email")String email) {
        try {
            FindArticleReq findArticleReq = new FindArticleReq(email,platformCode,-1l);
            return ResponseEntity.ok(new BaseResponse<>(this.communityService.findPlatformArticles(findArticleReq)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }


    //명세서 작성 완
    @PostMapping("/community/article/new")
    public ResponseEntity<BaseResponse<BaseResponseStatus>> saveArticle(@RequestBody SaveArticleReq saveArticleReq) {
        try {
            communityService.saveArticle(saveArticleReq);
            return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }

    //명세서 작성 완
    @PostMapping("/community/comment/new")
    public ResponseEntity<BaseResponse<BaseResponseStatus>> saveComment(@RequestBody SaveCommentReq saveCommentReq) {
        try {
            communityService.saveComment(saveCommentReq);
            return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }

    //명세서 작성 완
    @PostMapping("/community/heart/click")
    public ResponseEntity<BaseResponse<BaseResponseStatus>> clickHeart(@RequestBody HeartClickReq heartClickReq) {
        try {
            communityService.clickHeart(heartClickReq);
            return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }
}
