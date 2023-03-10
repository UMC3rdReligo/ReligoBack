package com.umcreligo.umcback.domain.user.repository;

import com.umcreligo.umcback.domain.user.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = {"church", "location"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findWithJoinByIdAndStatus(Long UserId, User.UserStatus status);

    boolean existsByNicknameAndStatus(String nickName, User.UserStatus status);

    Optional<User> findByAuthId(String authId);

    Optional<User> findByEmailAndStatusAndSocialType(String email, User.UserStatus status, User.SocialType type);
}
