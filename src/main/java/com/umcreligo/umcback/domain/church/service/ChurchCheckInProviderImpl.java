package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.domain.ChurchRegistration;
import com.umcreligo.umcback.domain.church.domain.ChurchTrial;
import com.umcreligo.umcback.domain.church.repository.ChurchRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChurchCheckInProviderImpl implements ChurchCheckInProvider {
    private final ChurchRegistrationRepository churchRegistrationRepository;

    @Override
    public Optional<ChurchRegistration> findRegistration() {
        return Optional.empty();
    }

    @Override
    public List<ChurchTrial> findTrials() {
        return new ArrayList<>();
    }
}
