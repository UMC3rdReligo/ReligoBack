package com.umcreligo.umcback.domain.hashtag.domain;


import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "hashtag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class HashTag {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    @Column
    private String text;
}
