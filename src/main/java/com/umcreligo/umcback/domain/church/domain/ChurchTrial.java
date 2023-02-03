package com.umcreligo.umcback.domain.church.domain;

import com.umcreligo.umcback.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "church_trial")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class ChurchTrial {
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

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 45, nullable = false)
    private String phoneNum;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column
    private LocalDateTime scheduledDateTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChurchTrialStatus status;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum ChurchTrialStatus {
        ACTIVE,
        DELETED
    }

    public void delete() {
        this.status = ChurchTrialStatus.DELETED;
    }
}
