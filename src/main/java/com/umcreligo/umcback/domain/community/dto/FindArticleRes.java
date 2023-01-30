package com.umcreligo.umcback.domain.community.dto;

import com.umcreligo.umcback.domain.community.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindArticleRes {

    private Long articleId;

    private String writer;

    private int type;

    private String title;

    private String text;

    private int heartCnt;

    private List<Comment> comments;

}
