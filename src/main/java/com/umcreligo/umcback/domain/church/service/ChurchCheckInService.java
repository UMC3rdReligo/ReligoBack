package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.dto.ChurchSignUpMemberResult;
import com.umcreligo.umcback.domain.church.dto.ChurchSignUpTrialResult;

// TODO: 파라미터 정의, API 구체화
public interface ChurchCheckInService {
    ChurchSignUpMemberResult signUpMember();

    ChurchSignUpTrialResult signUpTrial();
}
