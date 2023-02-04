package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.dto.CreateChurchParam;
import com.umcreligo.umcback.domain.church.dto.UpdateChurchParam;

public interface ChurchService {
    Long createChurch(CreateChurchParam param);

    void updateChurch(UpdateChurchParam param);

    void deleteChurch(Long churchId);
}
