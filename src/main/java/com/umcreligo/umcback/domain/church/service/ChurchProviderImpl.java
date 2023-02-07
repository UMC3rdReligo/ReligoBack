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
import com.umcreligo.umcback.global.config.BaseException;
import com.umcreligo.umcback.global.config.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
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

    public List<FindChurchResult> findChurches(List<Long> orderedChurchIds) {
        List<Church> churches = this.churchRepository.findAllWithJoinByIdInAndStatus(orderedChurchIds, Church.ChurchStatus.ACTIVE);
        List<Long> foundIds = churches.stream().map(Church::getId).collect(Collectors.toList());
        List<ChurchHashTag> hashTags = this.churchHashTagRepository.findAllByChurchIdInOrderByIdAsc(foundIds);
        List<ChurchImage> mainImages = this.churchImageRepository.findAllByChurchIdInAndTypeAndStatus(foundIds,
            ChurchImage.ChurchImageType.MAIN, ChurchImage.ChurchImageStatus.ACTIVE);
        List<ChurchImage> detailImages = this.churchImageRepository.findAllByChurchIdInAndTypeAndStatus(foundIds,
            ChurchImage.ChurchImageType.DETAIL, ChurchImage.ChurchImageStatus.ACTIVE);

        return churches.stream().map(church -> {
            List<ChurchHashTag> myHashTags = hashTags.stream()
                .filter(churchHashTag -> Objects.equals(churchHashTag.getChurch().getId(), church.getId()))
                .collect(Collectors.toList());
            List<ChurchImage> myMainImages = mainImages.stream()
                .filter(churchImage -> Objects.equals(churchImage.getChurch().getId(), church.getId()))
                .collect(Collectors.toList());
            List<ChurchImage> myDetailImages = detailImages.stream()
                .filter(churchImage -> Objects.equals(churchImage.getChurch().getId(), church.getId()))
                .collect(Collectors.toList());

            return this.createFindChurchResult(church, myHashTags, myMainImages, myDetailImages);
        }).sorted((o1, o2) -> {
            int a = orderedChurchIds.indexOf(o1.getInfo().getId());
            int b = orderedChurchIds.indexOf(o2.getInfo().getId());

            if (a == -1 && b == -1) {
                return 0;
            } else if (a == -1) {
                return 1;
            } else if (b == -1) {
                return -1;
            } else {
                return a - b;
            }
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<FindChurchResult> findChurch(Long churchId) {
        try {
            Church church = this.churchRepository.findWithJoinByIdAndStatus(churchId, Church.ChurchStatus.ACTIVE).orElseThrow();
            List<ChurchHashTag> hashTags = this.churchHashTagRepository.findAllByChurchIdOrderByIdAsc(church.getId());
            List<ChurchImage> mainImages = this.churchImageRepository.findAllByChurchIdAndTypeAndStatus(church.getId(),
                ChurchImage.ChurchImageType.MAIN, ChurchImage.ChurchImageStatus.ACTIVE);
            List<ChurchImage> detailImages = this.churchImageRepository.findAllByChurchIdAndTypeAndStatus(church.getId(),
                ChurchImage.ChurchImageType.DETAIL, ChurchImage.ChurchImageStatus.ACTIVE);

            return Optional.of(this.createFindChurchResult(church, hashTags, mainImages, detailImages));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<FindChurchResult> recommendChurches(Long userId) {
        User user = this.userRepository.findById(userId).orElse(null);
        this.checkUserExists(user);

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
                    .filter(termQuery("status", Church.ChurchStatus.ACTIVE.name()))
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

        return this.elasticsearchChurches(query);
    }

    @Override
    public List<FindChurchResult> searchChurches(Long userId, String platformCode, String hashtagCode, String keyword) {
        User user = this.userRepository.findById(userId).orElse(null);
        this.checkUserExists(user);

        String userLocationCode = StringUtils.defaultString(Objects.nonNull(user.getLocation()) ? user.getLocation().getCode() : null);
        String userCountryCode = Location.builder().code(userLocationCode).build().getCountryCode();
        String userCityCode = Location.builder().code(userLocationCode).build().getCityCode();

        BoolQueryBuilder boolQueryBuilder = boolQuery()
            .filter(termQuery("status", Church.ChurchStatus.ACTIVE.name()))
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

        return this.elasticsearchChurches(query);
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

    private List<FindChurchResult> elasticsearchChurches(Query query) {
        return this.findChurches(this.elasticsearchOperations.search(query, ChurchIndexDocument.class).stream()
            .map(SearchHit::getId)
            .filter(Objects::nonNull)
            .map(id -> {
                try {
                    return Long.valueOf(id);
                } catch (NumberFormatException e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList()));
    }

    private FindChurchResult createFindChurchResult(Church church, List<ChurchHashTag> hashTags,
                                                    List<ChurchImage> mainImages, List<ChurchImage> detailImages) {
        FindChurchResult result = new FindChurchResult();
        result.setInfo(church);
        result.setHashTags(hashTags.stream().map(churchHashTag -> churchHashTag.getHashTag().getCode()).collect(Collectors.toList()));
        result.setMainImage(mainImages.isEmpty() ? "" : mainImages.get(0).getUrl());
        result.setDetailImages(detailImages.stream().map(ChurchImage::getUrl).collect(Collectors.toList()));
        return result;
    }

    private void checkUserExists(User user) throws BaseException {
        if (user == null) {
            throw new BaseException(BaseResponseStatus.INVALID_USER_ID);
        }
    }
}
