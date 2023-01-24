package com.umcreligo.umcback.domain.church.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "church_hashtag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class ChurchHashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "churchId", nullable = false)
    @ToString.Exclude
    private Church church;

    // TODO: HashTag 엔티티 추가되면 @JoinColumn 설정
    @Column(nullable = false)
    @ToString.Exclude
    private Long hashTag;

    @Column
    private LocalDateTime createdAt;
}
