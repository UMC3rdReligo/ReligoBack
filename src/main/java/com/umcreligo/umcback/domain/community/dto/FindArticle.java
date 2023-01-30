package com.umcreligo.umcback.domain.community.dto;

import com.umcreligo.umcback.domain.community.domain.Article;
import com.umcreligo.umcback.domain.community.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindArticle {
    private Article article;

    private int heartCnt;

    private List<Comment> comments;

}
