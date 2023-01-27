package com.umcreligo.umcback.domain.community.domain;

import com.umcreligo.umcback.domain.church.domain.Church;
import com.umcreligo.umcback.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userID;

    @Column
    @Enumerated(EnumType.STRING)
    private CommunityType type;

    @ManyToOne
    @JoinColumn(name = "church_id")
    private Church churchId;

    @Column
    private String title;

    @Column
    private String text;

    @Column
    private int heartCount;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

}
