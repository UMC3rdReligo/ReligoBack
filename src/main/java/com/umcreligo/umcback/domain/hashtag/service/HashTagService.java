package com.umcreligo.umcback.domain.hashtag.service;


import com.umcreligo.umcback.domain.hashtag.domain.HashTag;
import com.umcreligo.umcback.domain.hashtag.dto.PopularHashTagRes;
import com.umcreligo.umcback.domain.hashtag.repository.HashTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HashTagService {
    private final HashTagRepository hashTagRepository;

    public PopularHashTagRes popularSearch(){
        PageRequest pageRequest = PageRequest.of(0,10);
        List<HashTag> hashTagList = hashTagRepository.findAllByOrderByUserCountDesc(pageRequest);
        List<PopularHashTagRes.HashTagDto> hashTags = hashTagList
            .stream()
            .map(hashTag -> new PopularHashTagRes.HashTagDto(hashTag.getText(),hashTag.getUserCount()))
            .collect(Collectors.toList());
        PopularHashTagRes popularHashTagRes = new PopularHashTagRes();
        popularHashTagRes.setHashtags(hashTags);
        return popularHashTagRes;
    }
}
