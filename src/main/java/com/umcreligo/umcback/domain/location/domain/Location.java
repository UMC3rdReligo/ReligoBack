package com.umcreligo.umcback.domain.location.domain;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "location")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class Location {
    @Id
    @Column(length = 45, nullable = false)
    @Comment("행정동코드")
    private String code;

    @Column(length = 45, nullable = false)
    @Comment("시도명")
    private String address1;

    @Column(length = 45, nullable = false)
    @Comment("시군구명")
    private String address2;

    @Column(length = 45, nullable = false)
    @Comment("읍면동명")
    private String address3;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LocationStatus status;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public enum LocationStatus {
        ACTIVE,
        DELETED
    }

    public String getCountryCode() {
        return StringUtils.left(this.code, 2);
    }

    public String getCityCode() {
        return StringUtils.left(this.code, 5);
    }
}
