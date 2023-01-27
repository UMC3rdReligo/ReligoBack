package com.umcreligo.umcback.domain.community.domain;

import com.umcreligo.umcback.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Comment")
public class Comment{

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article articleId;

    @Column
    private String text;
}
