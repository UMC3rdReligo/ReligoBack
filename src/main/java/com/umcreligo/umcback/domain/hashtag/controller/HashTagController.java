package com.umcreligo.umcback.domain.hashtag.controller;


import com.umcreligo.umcback.domain.hashtag.dto.PopularHashTagRes;
import com.umcreligo.umcback.domain.hashtag.service.HashTagService;
import com.umcreligo.umcback.global.config.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/hashtag")
@RequiredArgsConstructor
public class HashTagController {

    private final HashTagService hashTagService;

    @GetMapping("/popular")
    public ResponseEntity<BaseResponse<PopularHashTagRes>> popularSearch(){
        return ResponseEntity.ok(new BaseResponse<>(this.hashTagService.popularSearch()));
    }
}
