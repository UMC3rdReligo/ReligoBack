package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.dto.SignUpChurchMemberParam;
import com.umcreligo.umcback.domain.church.dto.SignUpChurchTrialParam;

public interface ChurchCheckInService {
    void signUpChurchMember(SignUpChurchMemberParam param);

    void signUpChurchTrial(SignUpChurchTrialParam param);

    void withdrawChurchMember(Long userId);

    void withdrawChurchTrial(Long userId, Long trialId);
}
