package com.umcreligo.umcback.domain.location.repository;

import com.umcreligo.umcback.domain.location.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, String> {
    Optional<Location> findByCode(String code);
}
