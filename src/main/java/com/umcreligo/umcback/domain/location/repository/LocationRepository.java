package com.umcreligo.umcback.domain.location.repository;

import com.umcreligo.umcback.domain.location.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, String> {
}
