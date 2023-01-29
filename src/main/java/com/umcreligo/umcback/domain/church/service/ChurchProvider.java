package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.dto.FindChurchResult;

import java.util.List;
import java.util.Optional;

public interface ChurchProvider {
    Optional<FindChurchResult> findChurch(Long churchId);

    List<FindChurchResult> recommendChurches(Long userId);

    List<FindChurchResult> searchChurches(Long userId, String platformCode, String hashtagCode, String keyword);
}
