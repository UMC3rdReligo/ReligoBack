package com.umcreligo.umcback.domain.church;

import com.umcreligo.umcback.domain.church.dto.*;
import com.umcreligo.umcback.domain.church.service.ChurchCheckInService;
import com.umcreligo.umcback.domain.church.service.ChurchProvider;
import com.umcreligo.umcback.global.config.BaseResponse;
import com.umcreligo.umcback.global.config.BaseResponseStatus;
import com.umcreligo.umcback.global.config.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/churches")
public class ChurchController {
    private final ChurchProvider churchProvider;
    private final ChurchCheckInService churchCheckInService;
    private final JwtService jwtService;

    @GetMapping("/{churchId}")
    public ResponseEntity<BaseResponse<FindChurchResult>> findChurch(@PathVariable("churchId") Long churchId) {
        try {
            return ResponseEntity.ok(new BaseResponse<>(this.churchProvider.findChurch(churchId).orElseThrow()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }

    @PostMapping("/{churchId}/registrations")
    public ResponseEntity<BaseResponse<Boolean>> signUpChurchMember(@PathVariable("churchId") Long churchId,
                                                                    @Valid @RequestBody SignUpChurchMemberRequest request) {
        Long userId = this.jwtService.getId();

        SignUpChurchMemberParam param = new SignUpChurchMemberParam();
        param.setUserId(userId);
        param.setChurchId(churchId);
        param.setName(request.getName());
        param.setBirthday(request.getBirthday());
        param.setPhoneNum(request.getPhoneNum());
        param.setAddress(request.getAddress());
        param.setReferee(request.getReferee());
        param.setMessage(request.getMessage());
        param.setScheduledDateTime(request.getScheduledDate() != null ? request.getScheduledDate().atStartOfDay() : null);

        this.churchCheckInService.signUpChurchMember(param);
        return ResponseEntity.ok(new BaseResponse<>(true));
    }

    @PostMapping("/{churchId}/trials")
    public ResponseEntity<BaseResponse<Boolean>> signUpChurchTrial(@PathVariable("churchId") Long churchId,
                                                                   @Valid @RequestBody SignUpChurchTrialRequest request) {
        Long userId = this.jwtService.getId();

        SignUpChurchTrialParam param = new SignUpChurchTrialParam();
        param.setUserId(userId);
        param.setChurchId(churchId);
        param.setName(request.getName());
        param.setPhoneNum(request.getPhoneNum());
        param.setMessage(request.getMessage());
        param.setScheduledDateTime(request.getScheduledDate() != null ? request.getScheduledDate().atStartOfDay() : null);

        this.churchCheckInService.signUpChurchTrial(param);
        return ResponseEntity.ok(new BaseResponse<>(true));
    }

    @GetMapping("/recommend")
    public ResponseEntity<BaseResponse<List<FindChurchResult>>> findRecommendChurches() {
        Long userId = this.jwtService.getId();
        return ResponseEntity.ok(new BaseResponse<>(this.churchProvider.recommendChurches(userId)));
    }

    @GetMapping("")
    public ResponseEntity<BaseResponse<List<FindChurchResult>>> findChurches(@RequestParam(value = "platformCode", required = false) String platformCode,
                                                                             @RequestParam(value = "hashtagCode", required = false) String hashtagCode,
                                                                             @RequestParam(value = "keyword", required = false) String keyword) {
        Long userId = this.jwtService.getId();
        return ResponseEntity.ok(new BaseResponse<>(this.churchProvider.searchChurches(userId,
            StringUtils.defaultString(platformCode), StringUtils.defaultString(hashtagCode), StringUtils.defaultString(keyword))));
    }
}
