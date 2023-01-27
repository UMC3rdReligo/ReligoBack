package com.umcreligo.umcback.domain.community.service;

import com.umcreligo.umcback.domain.community.repository.ArticleRepository;
import com.umcreligo.umcback.domain.community.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
//
//    //유저이름 -> 커뮤니티 게시글 조회(전체)
//    public void findAllCommunities(){
//
//    }
//
//    //유저이름 -> 커뮤니티 조회(교회)
//    public void findChurchCommunities(){
//
//    }
//
//    //유저이름 -> 커뮤니티 조회(교단)
//    public void findPlatformCommunities(){
//
//    }
//
//    //최근 20개 게시글 조회
//    public void findRecentlyArticles20(){
//
//    }

}
