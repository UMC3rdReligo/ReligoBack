package com.umcreligo.umcback.domain.community.dto;

import com.umcreligo.umcback.domain.community.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailArticleRes {

    private Long articleId;

    private String writer;

    private String title;

    private String text;

    private int heartCnt;

    private int commentCnt;

    private boolean isHearted;

    private String createAt;

    private List<ResponseCommentDto> comments;

}
