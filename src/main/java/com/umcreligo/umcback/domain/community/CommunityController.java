package com.umcreligo.umcback.domain.community;

import com.amazonaws.Response;
import com.umcreligo.umcback.domain.community.domain.CommunityType;
import com.umcreligo.umcback.domain.community.dto.FindArticle;
import com.umcreligo.umcback.domain.community.dto.SaveArticleReq;
import com.umcreligo.umcback.domain.community.service.CommunityService;
import com.umcreligo.umcback.domain.user.dto.UserChurchRes;
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

    @GetMapping("/community/article/{communityType}")
    public ResponseEntity<BaseResponse<List<FindArticle>>> getAllArticle(@PathVariable("communityType")CommunityType type){
        try {
            return ResponseEntity.ok(new BaseResponse<>(this.communityService.findCommunities(type)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }


    @PostMapping("/community/article/new")
    public ResponseEntity<BaseResponse<BaseResponseStatus>> getAllArticle(@RequestBody SaveArticleReq saveArticleReq){
        try {
            communityService.saveArticle(saveArticleReq);
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
