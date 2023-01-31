package com.umcreligo.umcback.domain.community.domain;

import com.umcreligo.umcback.domain.church.domain.Church;
import com.umcreligo.umcback.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private User user;

    @Column
    @Enumerated(EnumType.STRING)
    private CommunityType type;

    @ManyToOne
    @JoinColumn(name = "church_id")
    private Church church;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false) // varchar(255) 방지
    private String text;

    @Column
    private int heartCount;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
