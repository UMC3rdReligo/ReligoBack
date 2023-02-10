package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.domain.Church;
import com.umcreligo.umcback.domain.church.domain.ChurchRegistration;
import com.umcreligo.umcback.domain.church.domain.ChurchTrial;
import com.umcreligo.umcback.domain.church.dto.SignUpChurchMemberParam;
import com.umcreligo.umcback.domain.church.dto.SignUpChurchTrialParam;
import com.umcreligo.umcback.domain.church.repository.ChurchRegistrationRepository;
import com.umcreligo.umcback.domain.church.repository.ChurchRepository;
import com.umcreligo.umcback.domain.church.repository.ChurchTrialRepository;
import com.umcreligo.umcback.domain.user.domain.User;
import com.umcreligo.umcback.domain.user.repository.UserRepository;
import com.umcreligo.umcback.global.config.BaseException;
import com.umcreligo.umcback.global.config.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Transactional
@Service
public class ChurchCheckInServiceImpl implements ChurchCheckInService {
    private final ChurchRepository churchRepository;
    private final ChurchRegistrationRepository churchRegistrationRepository;
    private final ChurchTrialRepository churchTrialRepository;
    private final UserRepository userRepository;

    @Override
    public void signUpChurchMember(SignUpChurchMemberParam param) {
        User user = this.userRepository.findWithJoinByIdAndStatus(param.getUserId(), User.UserStatus.ACTIVE).orElse(null);
        Church church = this.churchRepository.findWithJoinByIdAndStatus(param.getChurchId(), Church.ChurchStatus.ACTIVE).orElse(null);
        this.checkUserExists(user);
        this.checkChurchExists(church);

        if (user.getChurch() != null) {
            throw new BaseException(BaseResponseStatus.ALREADY_HAS_CHURCH);
        }

        ChurchRegistration churchRegistration = ChurchRegistration.builder()
            .user(user)
            .church(church)
            .name(param.getName())
            .birthday(param.getBirthday())
            .phoneNum(param.getPhoneNum())
            .address(param.getAddress())
            .email(param.getEmail())
            .referee(param.getReferee())
            .message(param.getMessage())
            .scheduledDateTime(param.getScheduledDateTime())
            .build();

        user.setName(churchRegistration.getName());
        user.setPhoneNum(churchRegistration.getPhoneNum());
        user.setAddress(churchRegistration.getAddress());
        user.setEmail(churchRegistration.getEmail());
        user.setChurch(church);

        this.churchRegistrationRepository.save(churchRegistration);
        this.userRepository.save(user);
    }

    @Override
    public void signUpChurchTrial(SignUpChurchTrialParam param) {
        User user = this.userRepository.findWithJoinByIdAndStatus(param.getUserId(), User.UserStatus.ACTIVE).orElse(null);
        Church church = this.churchRepository.findWithJoinByIdAndStatus(param.getChurchId(), Church.ChurchStatus.ACTIVE).orElse(null);
        this.checkUserExists(user);
        this.checkChurchExists(church);

        if (user.getChurch() == church) {
            throw new BaseException(BaseResponseStatus.ALREADY_JOINED_CHURCH);
        }

        ChurchTrial churchTrial = ChurchTrial.builder()
            .user(user)
            .church(church)
            .name(param.getName())
            .phoneNum(param.getPhoneNum())
            .message(param.getMessage())
            .scheduledDateTime(param.getScheduledDateTime())
            .status(ChurchTrial.ChurchTrialStatus.ACTIVE)
            .build();

        this.churchTrialRepository.save(churchTrial);
    }

    @Override
    public void withdrawChurchMember(Long userId) {
        User user = this.userRepository.findWithJoinByIdAndStatus(userId, User.UserStatus.ACTIVE).orElse(null);

        if (user == null) {
            return;
        }

        user.withdrawChurch();
    }

    @Override
    public void withdrawChurchTrial(Long userId, Long trialId) {
        ChurchTrial churchTrial = this.churchTrialRepository.findById(trialId).orElse(null);

        if (churchTrial == null) {
            return;
        }

        if (userId == null || !Objects.equals(churchTrial.getUser().getId(), userId)) {
            return;
        }

        churchTrial.delete();
    }

    private void checkUserExists(User user) throws BaseException {
        if (user == null) {
            throw new BaseException(BaseResponseStatus.INVALID_USER_ID);
        }
    }

    private void checkChurchExists(Church church) throws BaseException {
        if (church == null) {
            throw new BaseException(BaseResponseStatus.INVALID_CHURCH_ID);
        }
    }
}
