package com.umcreligo.umcback.domain.community;

import com.umcreligo.umcback.domain.church.repository.PlatformRepository;
import com.umcreligo.umcback.domain.community.dto.FindArticleRes;
import com.umcreligo.umcback.domain.community.dto.HeartClickReq;
import com.umcreligo.umcback.domain.community.dto.SaveArticleReq;
import com.umcreligo.umcback.domain.community.dto.SaveCommentReq;
import com.umcreligo.umcback.domain.community.service.CommunityService;
import com.umcreligo.umcback.global.config.BaseResponse;
import com.umcreligo.umcback.global.config.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;
    private final PlatformRepository platformRepository;

    @GetMapping("/community/article/all")
    public ResponseEntity<BaseResponse<List<FindArticleRes>>> getAllArticle(){
        try {
            return ResponseEntity.ok(new BaseResponse<>(this.communityService.findAllArticles()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }
    @GetMapping("/community/article/church/{churchid}")
    public ResponseEntity<BaseResponse<List<FindArticleRes>>> getChurchArticle(@PathVariable("churchid")Long id){
        try {
            return ResponseEntity.ok(new BaseResponse<>(this.communityService.findChurchArticles(id)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }
    @GetMapping("/community/article/platform/{platformCode}")
    public ResponseEntity<BaseResponse<List<FindArticleRes>>> getPlatformArticle(@PathVariable("platformCode") String code){
        try {
            return ResponseEntity.ok(new BaseResponse<>(this.communityService.findPlatformArticles(code)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }





    @PostMapping("/community/article/new")
    public ResponseEntity<BaseResponse<BaseResponseStatus>> saveArticle(@RequestBody SaveArticleReq saveArticleReq){
        try {
            communityService.saveArticle(saveArticleReq);
            return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }
    @PostMapping("/community/comment/new")
    public ResponseEntity<BaseResponse<BaseResponseStatus>> saveComment(@RequestBody SaveCommentReq saveCommentReq){
        try {
            communityService.saveComment(saveCommentReq);
            return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }

    @PostMapping("/community/heart/click")
    public ResponseEntity<BaseResponse<BaseResponseStatus>> clickHeart(@RequestBody HeartClickReq heartClickReq){
        try {
            communityService.clickHeart(heartClickReq);
            return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }

    @GetMapping("/church")
    public ResponseEntity<BaseResponse> test(){
        return ResponseEntity.ok(new BaseResponse<>(communityService.test()));
    }
    @GetMapping("/church2")
    public ResponseEntity<BaseResponse> test2(){
        return ResponseEntity.ok(new BaseResponse<>(communityService.test2()));
    }
}
