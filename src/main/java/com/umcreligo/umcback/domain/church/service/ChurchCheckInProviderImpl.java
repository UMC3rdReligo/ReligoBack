package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.domain.ChurchRegistration;
import com.umcreligo.umcback.domain.church.domain.ChurchTrial;
import com.umcreligo.umcback.domain.church.dto.FindRegistrationResult;
import com.umcreligo.umcback.domain.church.dto.FindTrialResult;
import com.umcreligo.umcback.domain.church.repository.ChurchRegistrationRepository;
import com.umcreligo.umcback.domain.church.repository.ChurchTrialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChurchCheckInProviderImpl implements ChurchCheckInProvider {
    private final ChurchRegistrationRepository churchRegistrationRepository;
    private final ChurchTrialRepository churchTrialRepository;

    @Override
    public Optional<FindRegistrationResult> findRegistration(Long userId) {
        ChurchRegistration churchRegistration = this.churchRegistrationRepository.findTopWithJoinByUserIdOrderByIdDesc(userId).orElse(null);

        if (churchRegistration == null) {
            return Optional.empty();
        }

        FindRegistrationResult result = new FindRegistrationResult();
        result.setId(churchRegistration.getId());
        result.setUserId(churchRegistration.getUser().getId());
        result.setChurchId(churchRegistration.getChurch().getId());
        result.setName(churchRegistration.getName());
        result.setBirthday(churchRegistration.getBirthday());
        result.setPhoneNum(churchRegistration.getPhoneNum());
        result.setAddress(churchRegistration.getAddress());
        result.setReferee(churchRegistration.getReferee());
        result.setMessage(churchRegistration.getMessage());
        result.setScheduledDateTime(churchRegistration.getScheduledDateTime());
        result.setCreatedAt(churchRegistration.getCreatedAt());
        return Optional.of(result);
    }

    @Override
    public List<FindTrialResult> findTrials(Long userId, int pageSize) {
        List<ChurchTrial> churchTrials = this.churchTrialRepository.findWithJoinByUserIdAndStatusOrderByIdDesc(
            userId, ChurchTrial.ChurchTrialStatus.ACTIVE, Pageable.ofSize(pageSize));

        return churchTrials.stream().map(churchTrial -> {
            FindTrialResult result = new FindTrialResult();
            result.setId(churchTrial.getId());
            result.setUserId(churchTrial.getUser().getId());
            result.setChurchId(churchTrial.getChurch().getId());
            result.setName(churchTrial.getName());
            result.setPhoneNum(churchTrial.getPhoneNum());
            result.setMessage(churchTrial.getMessage());
            result.setScheduledDateTime(churchTrial.getScheduledDateTime());
            result.setCreatedAt(churchTrial.getCreatedAt());
            return result;
        }).collect(Collectors.toList());
    }
}
