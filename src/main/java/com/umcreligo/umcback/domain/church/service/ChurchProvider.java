package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.domain.Church;
import com.umcreligo.umcback.domain.church.dto.FindChurchResult;

import java.util.List;
import java.util.Optional;

// TODO: 파라미터 정의, API 구체화
public interface ChurchProvider {
    Optional<FindChurchResult> findChurch(Long churchId);

    List<Church> searchChurches();

    List<Church> recommendChurches();
}
