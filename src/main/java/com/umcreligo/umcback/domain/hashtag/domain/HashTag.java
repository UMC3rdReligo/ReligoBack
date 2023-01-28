package com.umcreligo.umcback.domain.hashtag.domain;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "hashtag")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class HashTag {
    @Id
    @Column(length = 45, nullable = false)
    private String code;

    @Column
    private String text;

    @Column
    @ColumnDefault("0")
    private Long userCount;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;
}
