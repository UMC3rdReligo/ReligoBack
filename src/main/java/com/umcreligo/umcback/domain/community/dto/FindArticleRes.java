package com.umcreligo.umcback.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindArticleRes {

    private Long articleId;

    private String writer;

    private String type;

    private String title;

    private String text;

    private int heartCnt;

    private Map<String,Object> comments;

    private boolean isHearted;
}
