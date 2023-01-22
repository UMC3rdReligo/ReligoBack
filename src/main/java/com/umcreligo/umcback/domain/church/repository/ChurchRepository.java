package com.umcreligo.umcback.domain.church.repository;

import com.umcreligo.umcback.domain.church.domain.Church;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChurchRepository extends JpaRepository<Church, Long> {
}
