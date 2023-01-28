package com.umcreligo.umcback.domain.hashtag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class PopularHashTagRes {
    List<HashTagDto> hashtags;

    @Data
    @AllArgsConstructor
    public static class HashTagDto{
        String code;
        Long count;
    }
}
