package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.domain.Church;
import org.springframework.data.domain.Page;

import java.util.Optional;

// TODO: 파라미터 정의, API 구체화
public interface ChurchProvider {
    Optional<Church> findChurch();

    Page<Church> searchChurches();

    Page<Church> recommendChurches();
}
