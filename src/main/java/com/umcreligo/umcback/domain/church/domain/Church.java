package com.umcreligo.umcback.domain.church.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.umcreligo.umcback.domain.location.domain.Location;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "church")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum ChurchStatus {
        ACTIVE,
        DELETED
    }

    public void update(Platform platform, Location location, String name, String address, String homepageURL,
                       String introduction, String minister, String schedule, String phoneNum) {
        if (platform != null) {
            this.platform = platform;
        }

        if (location != null) {
            this.location = location;
        }

        if (name != null) {
            this.name = name;
        }

        if (address != null) {
            this.address = address;
        }

        if (homepageURL != null) {
            this.homepageURL = homepageURL;
        }

        if (introduction != null) {
            this.introduction = introduction;
        }

        if (minister != null) {
            this.minister = minister;
        }

        if (schedule != null) {
            this.schedule = schedule;
        }

        if (phoneNum != null) {
            this.phoneNum = phoneNum;
        }
    }

    public void delete() {
        this.status = ChurchStatus.DELETED;
    }
}
