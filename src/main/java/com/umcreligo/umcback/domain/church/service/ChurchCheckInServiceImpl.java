package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.dto.SignUpChurchMemberResult;
import com.umcreligo.umcback.domain.church.dto.SignUpChurchTrialResult;
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
    public SignUpChurchMemberResult signUpMember() {
        return new SignUpChurchMemberResult();
    }

    @Override
    public SignUpChurchTrialResult signUpTrial() {
        return new SignUpChurchTrialResult();
    }
}
