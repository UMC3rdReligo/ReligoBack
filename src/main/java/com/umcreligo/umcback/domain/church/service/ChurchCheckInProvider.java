package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.dto.FindRegistrationResult;
import com.umcreligo.umcback.domain.church.dto.FindTrialResult;

import java.util.List;
import java.util.Optional;

public interface ChurchCheckInProvider {
    Optional<FindRegistrationResult> findRegistration(Long userId);

    List<FindTrialResult> findTrials(Long userId, int pageSize);
}
