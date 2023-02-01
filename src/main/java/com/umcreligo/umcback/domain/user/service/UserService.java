package com.umcreligo.umcback.domain.user.service;

import com.umcreligo.umcback.domain.church.dto.FindChurchResult;
import com.umcreligo.umcback.domain.church.service.ChurchProvider;
import com.umcreligo.umcback.domain.hashtag.domain.HashTag;
import com.umcreligo.umcback.domain.hashtag.repository.HashTagRepository;
import com.umcreligo.umcback.domain.location.domain.Location;
import com.umcreligo.umcback.domain.location.repository.LocationRepository;
import com.umcreligo.umcback.domain.user.domain.User;
import com.umcreligo.umcback.domain.user.domain.UserHashTag;
import com.umcreligo.umcback.domain.user.domain.UserServey;
import com.umcreligo.umcback.domain.user.dto.SignUpReq;
import com.umcreligo.umcback.domain.user.dto.UserInfoRes;
import com.umcreligo.umcback.domain.user.repository.UserHashTagRepository;
import com.umcreligo.umcback.domain.user.repository.UserRepository;
import com.umcreligo.umcback.domain.user.repository.UserServeyRepository;
import com.umcreligo.umcback.global.config.BaseException;
import com.umcreligo.umcback.global.config.BaseResponseStatus;
import com.umcreligo.umcback.global.config.security.jwt.JwtService;
import com.umcreligo.umcback.global.config.security.jwt.exception.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.umcreligo.umcback.global.config.security.jwt.exception.JwtErrorCode.USER_NOT_FOUND;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final HashTagRepository hashTagRepository;

    private final UserHashTagRepository userHashTagRepository;

    private final LocationRepository locationRepository;
    private final UserServeyRepository userServeyRepository;

    public void signup(SignUpReq signUpReq) throws NoSuchElementException{
        User user = userRepository.findByEmail(jwtService.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("가입된 이메일이 존재하지 않습니다."));
        UserServey userServey1 = new UserServey("Q1",signUpReq.getQuestion_1(),user);
        userServeyRepository.save(userServey1);
        UserServey userServey2 = new UserServey("Q2",signUpReq.getQuestion_2(),user);
        userServeyRepository.save(userServey2);
        UserServey userServey3 = new UserServey("Q3",signUpReq.getQuestion_3(),user);
        userServeyRepository.save(userServey3);
        UserServey userServey4 = new UserServey("Q4",signUpReq.getQuestion_4(),user);
        userServeyRepository.save(userServey4);
        UserServey userServey5 = new UserServey("Q5",signUpReq.getQuestion_5(),user);
        userServeyRepository.save(userServey5);
        UserServey userServey6 = new UserServey("Q6",signUpReq.getQuestion_6(),user);
        userServeyRepository.save(userServey6);
        UserServey userServey7 = new UserServey("Q7",signUpReq.getQuestion_7(),user);
        userServeyRepository.save(userServey7);
        UserServey userServey8 = new UserServey("Q8",signUpReq.getQuestion_8(),user);
        userServeyRepository.save(userServey8);
        UserServey userServey9 = new UserServey("Q9",signUpReq.getQuestion_9(),user);
        userServeyRepository.save(userServey9);
        signUpReq.getHashTag().stream().forEach(hashtag -> SaveUserHashTag(hashtag,user) );
        Location location = locationRepository.findByCode(signUpReq.getLocationCode()).orElseThrow();
        user.setLocation(location);
        user.setAddress(signUpReq.getAddress());
        user.setNickname(signUpReq.getNickname());
    }
    private void SaveUserHashTag(String text,User user) throws NoSuchElementException{
        HashTag hashTag = hashTagRepository.findByText(text).orElseThrow();
        hashTag.setUserCount(hashTag.getUserCount()+1);
        UserHashTag userHashTag = new UserHashTag();
        userHashTag.setUser(user);
        userHashTag.setHashTag(hashTag);
        userHashTagRepository.save(userHashTag);

    }

    public void logout() {
        User user = userRepository.findById(jwtService.getId())
                .orElseThrow(() -> new JwtException(USER_NOT_FOUND));
        user.deleteRefreshToken();
    }

    public UserInfoRes findInfoByUser() throws NoSuchElementException {
        User user = userRepository.findWithJoinByIdAndStatus(jwtService.getId(), User.UserStatus.ACTIVE).orElseThrow();
        UserInfoRes UserInfoRes = createUserInfo(user);
        return UserInfoRes;
    }

    private UserInfoRes createUserInfo(User user){
        UserInfoRes UserInfoRes = new UserInfoRes();
        UserInfoRes.setName(user.getName()==null ? "" : user.getName());
        UserInfoRes.setNickname(user.getNickname()==null ? "" : user.getNickname());
        UserInfoRes.setAddress(user.getAddress()==null ? "" : user.getAddress());
        UserInfoRes.setLocationCode(user.getLocation()==null ? "" : user.getLocation().getCode());
        UserInfoRes.setUserAddress1(user.getLocation()==null ? "" : user.getLocation().getAddress1());
        UserInfoRes.setUserAddress2(user.getLocation()==null ? "" : user.getLocation().getAddress2());
        UserInfoRes.setUserAddress3(user.getLocation()==null ? "" : user.getLocation().getAddress3());
        UserInfoRes.setChurchId(user.getChurch()==null ? 0 : user.getChurch().getId());
        UserInfoRes.setChurchName(user.getChurch()==null ? "" : user.getChurch().getName());
        UserInfoRes.setChurchAddress(user.getChurch()==null ? "" : user.getChurch().getAddress());
        return UserInfoRes;
    }

    public void checkNickName(String nickName) throws BaseException{
        if(userRepository.existsByNicknameAndStatus(nickName, User.UserStatus.ACTIVE)){
            throw new BaseException(BaseResponseStatus.NICKNAME_DUPLICATE);
        }
    }



    //refresh는 잠시 보류
//    public LoginTokenRes refresh(HttpServletRequest request) {
//        // Refresh Token 유효성 검사
//        String refreshToken = jwtService.getToken(request);
//        DecodedJWT decodedJWT = jwtService.verifyToken(refreshToken);
//
//        String email = jwtService.getEmail(refreshToken);
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new JwtException(INVALID_TOKEN));
//
//        if (user.getRefreshToken() == null || !user.getRefreshToken().equals(refreshToken)) {
//            throw new JwtException(INVALID_TOKEN);
//        }
//
//        // refresh token 유효성 검사 완료 후 -> access token 재발급
//        String accessToken = jwtService.createAccessToken(user.getEmail(), user.getId());
//        LoginTokenRes tokenResponse = new LoginTokenRes(accessToken, null);
//
//        // Refresh Token 만료시간 계산해 1개월 미만일 시 refresh token도 발급
//        long diffDays = jwtService.calculateRefreshExpiredDays(decodedJWT);
//        if (diffDays < TOKEN_REFRESH_DAYS) {
//            String newRefreshToken = jwtService.createRefreshToken(user.getEmail());
//            tokenResponse.setRefreshToken(newRefreshToken);
//            user.updateRefreshToken(newRefreshToken);
//        }
//        return tokenResponse;
//    }
}

