package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.dto.SignUpChurchMemberResult;
import com.umcreligo.umcback.domain.church.dto.SignUpChurchTrialResult;

// TODO: 파라미터 정의, API 구체화
public interface ChurchCheckInService {
    SignUpChurchMemberResult signUpMember();

    SignUpChurchTrialResult signUpTrial();
}
