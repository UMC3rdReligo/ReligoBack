package com.umcreligo.umcback.domain.community.dto;

import com.umcreligo.umcback.domain.church.domain.Church;
import com.umcreligo.umcback.domain.community.domain.Article;
import com.umcreligo.umcback.domain.community.domain.Comment;
import com.umcreligo.umcback.domain.community.domain.CommunityType;
import com.umcreligo.umcback.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindArticle {
    private String writer;

    private int type;

    private String title;

    private String text;

    private int heartCnt;

    private List<Comment> comments;

}
