package com.umcreligo.umcback.domain.church;

import com.umcreligo.umcback.domain.church.dto.*;
import com.umcreligo.umcback.domain.church.service.ChurchCheckInService;
import com.umcreligo.umcback.domain.church.service.ChurchProvider;
import com.umcreligo.umcback.domain.church.service.ChurchService;
import com.umcreligo.umcback.global.config.BaseResponse;
import com.umcreligo.umcback.global.config.BaseResponseStatus;
import com.umcreligo.umcback.global.config.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/churches")
public class ChurchController {
    private final ChurchProvider churchProvider;
    private final ChurchService churchService;
    private final ChurchCheckInService churchCheckInService;
    private final JwtService jwtService;

    @GetMapping("")
    public ResponseEntity<BaseResponse<List<FindChurchResult>>> findChurches(@RequestParam(value = "platformCode", required = false) String platformCode,
                                                                             @RequestParam(value = "hashtagCode", required = false) String hashtagCode,
                                                                             @RequestParam(value = "keyword", required = false) String keyword) {
        Long userId = this.jwtService.getId();
        return ResponseEntity.ok(new BaseResponse<>(this.churchProvider.searchChurches(userId,
            StringUtils.defaultString(platformCode), StringUtils.defaultString(hashtagCode), StringUtils.defaultString(keyword))));
    }

    @PostMapping("")
    public ResponseEntity<BaseResponse<Long>> createChurch(@Valid @RequestBody CreateChurchRequest request) {
        CreateOrUpdateChurchParam param = new CreateOrUpdateChurchParam();
        param.setPlatformCode(StringUtils.trimToEmpty(request.getPlatformCode()));
        param.setLocationCode(StringUtils.trimToEmpty(request.getLocationCode()));
        param.setName(StringUtils.trimToEmpty(request.getName()));
        param.setAddress(StringUtils.trimToEmpty(request.getAddress()));
        param.setHomepageURL(StringUtils.trimToEmpty(request.getHomepageURL()));
        param.setIntroduction(StringUtils.trimToEmpty(request.getIntroduction()));
        param.setMinister(StringUtils.trimToEmpty(request.getMinister()));
        param.setSchedule(StringUtils.trimToEmpty(request.getSchedule()));
        param.setPhoneNum(StringUtils.trimToEmpty(request.getPhoneNum()));
        param.setHashTags((request.getHashTags() != null) ?
            request.getHashTags().stream().map(StringUtils::trimToEmpty).collect(Collectors.toList()) : new ArrayList<>());
        param.setMainImage(StringUtils.trimToEmpty(request.getMainImage()));
        param.setDetailImages((request.getDetailImages() != null) ?
            request.getDetailImages().stream().map(StringUtils::trimToEmpty).collect(Collectors.toList()) : new ArrayList<>());

        return ResponseEntity.ok(new BaseResponse<>(this.churchService.createChurch(param)));
    }

    @GetMapping("/{churchId}")
    public ResponseEntity<BaseResponse<FindChurchResult>> findChurch(@PathVariable("churchId") Long churchId) {
        try {
            return ResponseEntity.ok(new BaseResponse<>(this.churchProvider.findChurch(churchId).orElseThrow()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }

    @PatchMapping("/{churchId}")
    public ResponseEntity<BaseResponse<Boolean>> updateChurch(@PathVariable("churchId") Long churchId,
                                                              @Valid @RequestBody UpdateChurchRequest request) {
        CreateOrUpdateChurchParam param = new CreateOrUpdateChurchParam();
        param.setId(churchId);
        param.setPlatformCode(StringUtils.trim(request.getPlatformCode()));
        param.setLocationCode(StringUtils.trim(request.getLocationCode()));
        param.setName(StringUtils.trim(request.getName()));
        param.setAddress(StringUtils.trim(request.getAddress()));
        param.setHomepageURL(StringUtils.trim(request.getHomepageURL()));
        param.setIntroduction(StringUtils.trim(request.getIntroduction()));
        param.setMinister(StringUtils.trim(request.getMinister()));
        param.setSchedule(StringUtils.trim(request.getSchedule()));
        param.setPhoneNum(StringUtils.trim(request.getPhoneNum()));
        param.setHashTags((request.getHashTags() != null) ?
            request.getHashTags().stream().map(StringUtils::trimToEmpty).collect(Collectors.toList()) : null);
        param.setMainImage(StringUtils.trim(request.getMainImage()));
        param.setDetailImages((request.getDetailImages() != null) ?
            request.getDetailImages().stream().map(StringUtils::trimToEmpty).collect(Collectors.toList()) : null);

        this.churchService.updateChurch(param);
        return ResponseEntity.ok(new BaseResponse<>(true));
    }

    @DeleteMapping("/{churchId}")
    public ResponseEntity<BaseResponse<Boolean>> deleteChurch(@PathVariable("churchId") Long churchId) {
        this.churchService.deleteChurch(churchId);
        return ResponseEntity.ok(new BaseResponse<>(true));
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
}
