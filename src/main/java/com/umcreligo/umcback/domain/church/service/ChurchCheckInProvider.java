package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.domain.ChurchRegistration;
import com.umcreligo.umcback.domain.church.domain.ChurchTrial;

import java.util.List;
import java.util.Optional;

// TODO: 파라미터 정의, API 구체화
public interface ChurchCheckInProvider {
    Optional<ChurchRegistration> findRegistration();

    List<ChurchTrial> findTrials();
}
