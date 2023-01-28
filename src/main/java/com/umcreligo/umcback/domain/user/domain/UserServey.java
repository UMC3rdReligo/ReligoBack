package com.umcreligo.umcback.domain.user.domain;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "userservey")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class UserServey {
    public static final String QUESTION_CODE_PREFIX = "Q";
    public static final long WANTED_PLATFORM_QUESTION_NUMBER = 4;
    public static final long PERSONALITY_QUESTION_NUMBER_MIN = 6;
    public static final long PERSONALITY_QUESTION_NUMBER_MAX = 11;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String questionCode;

    @Column(length = 45, nullable = false)
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    @ToString.Exclude
    private User user;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public void setUser(User user) {
        this.user = user;
    }

    public UserServey(String questionCode, String answer, User user) {
        this.questionCode = questionCode;
        this.answer = answer;
        this.user = user;
    }
}
