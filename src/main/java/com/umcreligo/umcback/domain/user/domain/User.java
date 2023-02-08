package com.umcreligo.umcback.domain.user.domain;


import com.umcreligo.umcback.domain.church.domain.Church;
import com.umcreligo.umcback.domain.location.domain.Location;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column
    private String authId;

    @Column
    private String name;
    @Column
    private String password;

    @Column
    private String profileImgUrl;

    @Column
    private String phoneNum;

    //LocationCode 필요
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locationCode")
    private Location location;

    @Column
    private String gender;

    @Enumerated(STRING)
    private Role role;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    @Enumerated(STRING)
    private UserStatus status;

    @Column(length = 300)
    private String email;

    @Column(length = 300)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "churchId")
    @ToString.Exclude
    private Church church;

    @Column
    private String nickname;

    @Column
    private String platform;  //선호하는 교단

    @Column
    private String refreshToken;

    @Column
    @Enumerated(value = STRING)
    private SocialType socialType;

    public enum UserStatus {
        ACTIVE,
        DELETED
    }

    public enum SocialType {
        KAKAO,
        NAVER
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void deleteRefreshToken() {
        this.refreshToken = null;
    }

    public void encodePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void withdrawChurch() {
        this.church = null;
    }
}
