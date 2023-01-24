package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.dto.ChurchSignUpMemberResult;
import com.umcreligo.umcback.domain.church.dto.ChurchSignUpTrialResult;
import com.umcreligo.umcback.domain.church.repository.ChurchRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChurchCheckInServiceImpl implements ChurchCheckInService {
    private final ChurchRegistrationRepository churchRegistrationRepository;

    @Override
    public ChurchSignUpMemberResult signUpMember() {
        return new ChurchSignUpMemberResult();
    }

    @Override
    public ChurchSignUpTrialResult signUpTrial() {
        return new ChurchSignUpTrialResult();
    }
}
