package com.umcreligo.umcback.domain.church.repository;

import com.umcreligo.umcback.domain.church.domain.ChurchTrial;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChurchTrialRepository extends JpaRepository<ChurchTrial, Long> {
    @EntityGraph(attributePaths = {"user", "church"}, type = EntityGraph.EntityGraphType.LOAD)
    List<ChurchTrial> findWithJoinByUserIdOrderByIdDesc(Long userId, Pageable pageable);
}
