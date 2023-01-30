package com.umcreligo.umcback.domain.community.dto;

import com.umcreligo.umcback.domain.community.domain.Article;
import com.umcreligo.umcback.domain.user.domain.User;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class SaveCommentReq {

    private Long articleId;

    private String email;

    private String text;
}
