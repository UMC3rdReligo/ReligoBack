package com.umcreligo.umcback.domain.church.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "platform")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Platform {
    public static final String GROUP_PA = "PA";
    public static final String GROUP_PB = "PB";
    public static final String GROUP_PC = "PC";

    @Id
    @Column(length = 45, nullable = false)
    private String code;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlatformStatus status;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum PlatformStatus {
        ACTIVE,
        DELETED
    }

    public String getGroupCode() {
        return StringUtils.left(this.code, 2);
    }
}
