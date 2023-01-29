package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.domain.*;
import com.umcreligo.umcback.domain.church.dto.FindChurchResult;
import com.umcreligo.umcback.domain.church.repository.ChurchHashTagRepository;
import com.umcreligo.umcback.domain.church.repository.ChurchImageRepository;
import com.umcreligo.umcback.domain.church.repository.ChurchRepository;
import com.umcreligo.umcback.domain.location.domain.Location;
import com.umcreligo.umcback.domain.user.domain.User;
import com.umcreligo.umcback.domain.user.domain.UserServey;
import com.umcreligo.umcback.domain.user.repository.UserHashTagRepository;
import com.umcreligo.umcback.domain.user.repository.UserRepository;
import com.umcreligo.umcback.domain.user.repository.UserServeyRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChurchProviderImpl implements ChurchProvider {
    private final ChurchRepository churchRepository;
    private final ChurchHashTagRepository churchHashTagRepository;
    private final ChurchImageRepository churchImageRepository;
    private final UserRepository userRepository;
    private final UserHashTagRepository userHashTagRepository;
    private final UserServeyRepository userServeyRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public Optional<FindChurchResult> findChurch(Long churchId) {
        try {
            Church church = this.churchRepository.findWithJoinByIdAndStatus(churchId, Church.ChurchStatus.ACTIVE).orElseThrow();
            List<ChurchHashTag> hashTags = this.churchHashTagRepository.findAllByChurchId(church.getId());
            List<ChurchImage> mainImages = this.churchImageRepository.findAllByChurchIdAndTypeAndStatus(church.getId(),
                ChurchImage.ChurchImageType.MAIN, ChurchImage.ChurchImageStatus.ACTIVE);
            List<ChurchImage> detailImages = this.churchImageRepository.findAllByChurchIdAndTypeAndStatus(church.getId(),
                ChurchImage.ChurchImageType.DETAIL, ChurchImage.ChurchImageStatus.ACTIVE);

            FindChurchResult result = new FindChurchResult();
            result.setInfo(church);
            result.setHashTags(hashTags.stream().map(churchHashTag -> churchHashTag.getHashTag().getCode()).collect(Collectors.toList()));
            result.setMainImage(mainImages.isEmpty() ? "" : mainImages.get(0).getUrl());
            result.setDetailImages(detailImages.stream().map(ChurchImage::getUrl).collect(Collectors.toList()));
            return Optional.of(result);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<FindChurchResult> recommendChurches(Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow();
        List<String> userHashtagCodes = this.userHashTagRepository.findAllByUserIdOrderByIdDesc(user.getId()).stream()
            .map(userHashTag -> userHashTag.getHashTag().getCode())
            .collect(Collectors.toList());
        List<UserServey> userServeys = this.userServeyRepository.findAllByUserIdOrderByIdDesc(user.getId());

        String userWantedPlatformCode = userServeys.stream()
            .filter(userServey -> userServey.getQuestionCode().equals(UserServey.QUESTION_CODE_PREFIX + UserServey.WANTED_PLATFORM_QUESTION_NUMBER))
            .map(UserServey::getAnswer)
            .findFirst().orElse("");
        String userWantedPlatformGroupCode = Platform.builder().code(userWantedPlatformCode).build().getGroupCode();
        String userPersonalityPlatformGroupCode = this.calcUserPersonalityPlatformGroupCode(userServeys);
        String userLocationCode = StringUtils.defaultString(Objects.nonNull(user.getLocation()) ? user.getLocation().getCode() : null);
        String userCountryCode = StringUtils.defaultString(Objects.nonNull(user.getLocation()) ? user.getLocation().getCountryCode() : null);
        String userCityCode = StringUtils.defaultString(Objects.nonNull(user.getLocation()) ? user.getLocation().getCityCode() : null);

        NativeSearchQuery query = new NativeSearchQueryBuilder()
            .withQuery(
                boolQuery()
                    // [필수] 같은 시도
                    .filter(prefixQuery("locationcode.keyword", userCountryCode))
                    // 같은 구 (중요도 상)
                    .should(constantScoreQuery(prefixQuery("locationcode.keyword", userCityCode)).boost(4))
                    // 같은 동 (중요도 최상)
                    .should(constantScoreQuery(termQuery("locationcode.keyword", userLocationCode)).boost(6))
                    // 설문조사 결과 성향의 교단 (중요도 중)
                    .should(constantScoreQuery(prefixQuery("platformcode.keyword", userPersonalityPlatformGroupCode)).boost(2))
                    // 유저가 경험한 성향의 교단 (중요도 상)
                    .should(constantScoreQuery(prefixQuery("platformcode.keyword", userWantedPlatformGroupCode)).boost(4))
                    // 유저가 경험한 교단 (중요도 상)
                    .should(constantScoreQuery(termQuery("platformcode.keyword", userWantedPlatformCode)).boost(4))
                    // 해시태그가 유사한 교회 (중요도 하)
                    .should(matchQuery("hashtags", StringUtils.join(userHashtagCodes, " ")).boost(1))
            )
            .withPageable(Pageable.ofSize(20))
            .build();

        return this.elasticsearchOperations.search(query, ChurchIndexDocument.class).stream()
            .map(SearchHit::getId).filter(Objects::nonNull)
            .map(id -> {
                try {
                    return this.findChurch(Long.valueOf(id)).orElse(null);
                } catch (NumberFormatException e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    @Override
    public List<FindChurchResult> searchChurches(Long userId, String platformCode, String hashtagCode, String keyword) {
        User user = this.userRepository.findById(userId).orElseThrow();
        String userLocationCode = StringUtils.defaultString(Objects.nonNull(user.getLocation()) ? user.getLocation().getCode() : null);
        String userCountryCode = Location.builder().code(userLocationCode).build().getCountryCode();
        String userCityCode = Location.builder().code(userLocationCode).build().getCityCode();

        BoolQueryBuilder boolQueryBuilder = boolQuery()
            // 같은 시도 (중요도 하)
            .should(constantScoreQuery(prefixQuery("locationcode.keyword", userCountryCode)).boost(2))
            // 같은 구 (중요도 하)
            .should(constantScoreQuery(prefixQuery("locationcode.keyword", userCityCode)).boost(2))
            // 같은 동 (중요도 하)
            .should(constantScoreQuery(termQuery("locationcode.keyword", userLocationCode)).boost(2));

        if (StringUtils.isNotEmpty(platformCode)) {
            // [필수] 선택한 교단
            boolQueryBuilder.filter(termQuery("platformcode.keyword", platformCode));
        }

        if (StringUtils.isNotEmpty(hashtagCode)) {
            // [필수] 선택한 해시태그 (중요도 최하)
            boolQueryBuilder.must(matchQuery("hashtags", hashtagCode).boost(1));
        }

        if (StringUtils.isNotEmpty(keyword)) {
            boolQueryBuilder
                .must(boolQuery()
                    // 교회 이름 (중요도 최상)
                    .should(matchQuery("name", keyword).boost(60))
                    .should(matchPhraseQuery("name", keyword).boost(60))
                    // 교회 주소 (중요도 상)
                    .should(matchQuery("address", keyword).boost(20))
                    .should(matchPhraseQuery("address", keyword).boost(20))
                    // 교회 소개글 (중요도 중)
                    .should(matchQuery("introduction", keyword).boost(10))
                    .should(matchPhraseQuery("introduction", keyword).boost(10))
                    .minimumShouldMatch(1)
                );
        }

        NativeSearchQuery query = new NativeSearchQueryBuilder()
            .withQuery(boolQueryBuilder)
            .withPageable(Pageable.ofSize(10))
            .build();

        return this.elasticsearchOperations.search(query, ChurchIndexDocument.class).stream()
            .map(SearchHit::getId).filter(Objects::nonNull)
            .map(id -> {
                try {
                    return this.findChurch(Long.valueOf(id)).orElse(null);
                } catch (NumberFormatException e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    private String calcUserPersonalityPlatformGroupCode(List<UserServey> userServeys) {
        /*
            전혀 아니다 (0점) ~ 매우 그렇다 (4점)

            총합 15점 이하면 PA
            총합 16 ~ 20점이면 PC
            총합 20점 이상이면 PB
         */

        long score = 0;

        for (long number = UserServey.PERSONALITY_QUESTION_NUMBER_MIN;
             number <= UserServey.PERSONALITY_QUESTION_NUMBER_MAX; number++) {
            String questionCode = UserServey.QUESTION_CODE_PREFIX + number;
            UserServey targetServey = userServeys.stream()
                .filter(userServey -> userServey.getQuestionCode().equals(questionCode))
                .findFirst().orElse(null);

            if (targetServey == null) {
                return "";
            }

            try {
                long curScore = Long.parseLong(targetServey.getAnswer());

                if (curScore < 0 || curScore > 4) {
                    throw new NumberFormatException();
                }

                score += curScore;
            } catch (NumberFormatException e) {
                return "";
            }
        }

        if (score <= 15) {
            return Platform.GROUP_PA;
        } else if (score >= 20) {
            return Platform.GROUP_PB;
        } else {
            return Platform.GROUP_PC;
        }
    }
}
