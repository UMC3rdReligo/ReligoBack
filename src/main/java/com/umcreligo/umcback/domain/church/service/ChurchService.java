package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.dto.CreateOrUpdateChurchParam;

public interface ChurchService {
    Long createChurch(CreateOrUpdateChurchParam param);

    void updateChurch(CreateOrUpdateChurchParam param);

    void deleteChurch(Long churchId);
}
