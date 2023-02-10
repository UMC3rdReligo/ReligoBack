package com.umcreligo.umcback.domain.church.domain;

import com.umcreligo.umcback.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "church_registration")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class ChurchRegistration {
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

    @Column
    private LocalDate birthday;

    @Column(length = 45, nullable = false)
    private String phoneNum;

    @Column(length = 300, nullable = false)
    private String address;

    @Column(length = 300, nullable = false)
    private String email;

    @Column(length = 45, nullable = false)
    private String referee;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column
    private LocalDateTime scheduledDateTime;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;
}
