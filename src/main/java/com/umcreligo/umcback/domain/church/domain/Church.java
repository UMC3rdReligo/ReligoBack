package com.umcreligo.umcback.domain.church.domain;

import com.umcreligo.umcback.domain.location.domain.Location;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "church")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class Church {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "platformCode", nullable = false)
    @ToString.Exclude
    private Platform platform;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "locationCode", nullable = false)
    @ToString.Exclude
    private Location location;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 300, nullable = false)
    private String address;

    @Column(length = 300, nullable = false)
    private String homepageURL;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String introduction;

    @Column(length = 45, nullable = false)
    private String minister;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String schedule;

    @Column(length = 45, nullable = false)
    private String phoneNum;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChurchStatus status;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public enum ChurchStatus {
        ACTIVE,
        DELETED
    }
}
