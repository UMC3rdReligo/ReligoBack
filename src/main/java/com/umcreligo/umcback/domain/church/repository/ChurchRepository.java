package com.umcreligo.umcback.domain.church.repository;

import com.umcreligo.umcback.domain.church.domain.Church;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChurchRepository extends JpaRepository<Church, Long> {
    @EntityGraph(attributePaths = {"platform", "location"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Church> findWithJoinByIdAndStatus(Long churchId, Church.ChurchStatus churchStatus);
}
