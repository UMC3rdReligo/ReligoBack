package com.umcreligo.umcback.domain.church.repository;

import com.umcreligo.umcback.domain.church.domain.ChurchTrial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChurchTrialRepository extends JpaRepository<ChurchTrial, Long> {
    @EntityGraph(attributePaths = {"user", "church"}, type = EntityGraph.EntityGraphType.LOAD)
    Page<ChurchTrial> findWithJoinByUserIdAndStatusOrderByIdDesc(Long userId, ChurchTrial.ChurchTrialStatus churchTrialStatus, Pageable pageable);
}
