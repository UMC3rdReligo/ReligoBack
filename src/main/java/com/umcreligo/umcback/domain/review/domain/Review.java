package com.umcreligo.umcback.domain.review.domain;

import com.umcreligo.umcback.domain.church.domain.Church;
import com.umcreligo.umcback.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "churchId", nullable = false)
    @ToString.Exclude
    private Church church;

    @Column(length = 300, nullable = false)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(nullable = false)
    private Boolean anonymous;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum ReviewStatus {
        ACTIVE,
        DELETED
    }

    public void update(String title, String text, Boolean anonymous) {
        if (title != null) {
            this.title = title;
        }

        if (text != null) {
            this.text = text;
        }

        if (anonymous != null) {
            this.anonymous = anonymous;
        }
    }

    public void delete() {
        this.status = ReviewStatus.DELETED;
    }
}
