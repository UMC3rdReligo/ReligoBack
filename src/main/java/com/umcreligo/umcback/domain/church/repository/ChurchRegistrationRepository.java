package com.umcreligo.umcback.domain.church.repository;

import com.umcreligo.umcback.domain.church.domain.ChurchRegistration;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChurchRegistrationRepository extends JpaRepository<ChurchRegistration, Long> {
    @EntityGraph(attributePaths = {"user", "church"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<ChurchRegistration> findTopWithJoinByUserIdOrderByIdDesc(Long userId);
}
