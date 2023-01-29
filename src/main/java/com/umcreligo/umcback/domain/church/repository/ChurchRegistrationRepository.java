package com.umcreligo.umcback.domain.church.repository;

import com.umcreligo.umcback.domain.church.domain.ChurchRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChurchRegistrationRepository extends JpaRepository<ChurchRegistration, Long> {
}
