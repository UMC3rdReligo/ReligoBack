package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.domain.Church;
import com.umcreligo.umcback.domain.church.domain.ChurchHashTag;
import com.umcreligo.umcback.domain.church.domain.ChurchImage;
import com.umcreligo.umcback.domain.church.domain.Platform;
import com.umcreligo.umcback.domain.church.dto.CreateOrUpdateChurchParam;
import com.umcreligo.umcback.domain.church.repository.ChurchHashTagRepository;
import com.umcreligo.umcback.domain.church.repository.ChurchImageRepository;
import com.umcreligo.umcback.domain.church.repository.ChurchRepository;
import com.umcreligo.umcback.domain.church.repository.PlatformRepository;
import com.umcreligo.umcback.domain.hashtag.domain.HashTag;
import com.umcreligo.umcback.domain.hashtag.repository.HashTagRepository;
import com.umcreligo.umcback.domain.location.domain.Location;
import com.umcreligo.umcback.domain.location.repository.LocationRepository;
import com.umcreligo.umcback.global.config.BaseException;
import com.umcreligo.umcback.global.config.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ChurchServiceImpl implements ChurchService {
    private final ChurchRepository churchRepository;
    private final ChurchHashTagRepository churchHashTagRepository;
    private final ChurchImageRepository churchImageRepository;
    private final PlatformRepository platformRepository;
    private final LocationRepository locationRepository;
    private final HashTagRepository hashTagRepository;

    @Override
    public Long createChurch(CreateOrUpdateChurchParam param) {
        Platform platform = this.platformRepository.findById(param.getPlatformCode()).orElse(null);

        if (platform == null) {
            throw new BaseException(BaseResponseStatus.INVALID_PLATFORM_CODE);
        }

        Location location = this.locationRepository.findById(param.getLocationCode()).orElse(null);

        if (location == null) {
            throw new BaseException(BaseResponseStatus.INVALID_LOCATION_CODE);
        }

        List<HashTag> hashTags = this.hashTagRepository.findAllByCodeInOrderByCodeAsc(param.getHashTags());

        if (hashTags.size() != param.getHashTags().size()) {
            throw new BaseException(BaseResponseStatus.INVALID_HASH_TAG);
        }

        Church church = Church.builder()
            .platform(platform)
            .location(location)
            .name(param.getName())
            .address(param.getAddress())
            .homepageURL(param.getHomepageURL())
            .introduction(param.getIntroduction())
            .minister(param.getMinister())
            .schedule(param.getSchedule())
            .phoneNum(param.getPhoneNum())
            .status(Church.ChurchStatus.ACTIVE)
            .build();

        this.churchRepository.saveAndFlush(church);

        List<ChurchHashTag> churchHashTags = hashTags.stream().map(hashTag -> ChurchHashTag.builder()
                .church(church)
                .hashTag(hashTag)
                .build())
            .collect(Collectors.toList());

        this.churchHashTagRepository.saveAll(churchHashTags);

        if (StringUtils.isNotBlank(param.getMainImage())) {
            ChurchImage mainImage = ChurchImage.builder()
                .church(church)
                .type(ChurchImage.ChurchImageType.MAIN)
                .url(param.getMainImage())
                .status(ChurchImage.ChurchImageStatus.ACTIVE)
                .build();

            this.churchImageRepository.save(mainImage);
        }

        this.saveDetailImages(church, param.getDetailImages().stream()
            .filter(StringUtils::isNotBlank)
            .collect(Collectors.toList()));

        return Objects.requireNonNull(church.getId());
    }

    @Override
    public void updateChurch(CreateOrUpdateChurchParam param) {
        Church church = this.churchRepository.findWithJoinByIdAndStatus(param.getId(), Church.ChurchStatus.ACTIVE).orElse(null);

        if (church == null) {
            throw new BaseException(BaseResponseStatus.NOT_FOUND);
        }

        Platform platform = null;

        if (param.getPlatformCode() != null) {
            platform = this.platformRepository.findById(param.getPlatformCode()).orElse(null);

            if (platform == null) {
                throw new BaseException(BaseResponseStatus.INVALID_PLATFORM_CODE);
            }
        }

        Location location = null;

        if (param.getLocationCode() != null) {
            location = this.locationRepository.findById(param.getLocationCode()).orElse(null);

            if (location == null) {
                throw new BaseException(BaseResponseStatus.INVALID_LOCATION_CODE);
            }
        }

        if (param.getHashTags() != null) {
            List<HashTag> hashTags = this.hashTagRepository.findAllByCodeInOrderByCodeAsc(param.getHashTags());

            if (hashTags.size() != param.getHashTags().size()) {
                throw new BaseException(BaseResponseStatus.INVALID_HASH_TAG);
            }

            this.churchHashTagRepository.deleteAll(this.churchHashTagRepository.findAllByChurchIdOrderByIdAsc(church.getId()));
            this.churchHashTagRepository.flush();

            List<ChurchHashTag> churchHashTags = hashTags.stream().map(hashTag -> ChurchHashTag.builder()
                    .church(church)
                    .hashTag(hashTag)
                    .build())
                .collect(Collectors.toList());

            this.churchHashTagRepository.saveAll(churchHashTags);
        }

        if (param.getMainImage() != null) {
            this.churchImageRepository.deleteAll(this.churchImageRepository.findAllByChurchIdAndTypeAndStatus(
                church.getId(), ChurchImage.ChurchImageType.MAIN, ChurchImage.ChurchImageStatus.ACTIVE));
            this.churchImageRepository.flush();

            if (StringUtils.isNotBlank(param.getMainImage())) {
                ChurchImage mainImage = ChurchImage.builder()
                    .church(church)
                    .type(ChurchImage.ChurchImageType.MAIN)
                    .url(param.getMainImage())
                    .status(ChurchImage.ChurchImageStatus.ACTIVE)
                    .build();

                this.churchImageRepository.save(mainImage);
            }
        }

        if (param.getDetailImages() != null) {
            this.churchImageRepository.deleteAll(this.churchImageRepository.findAllByChurchIdAndTypeAndStatus(
                church.getId(), ChurchImage.ChurchImageType.DETAIL, ChurchImage.ChurchImageStatus.ACTIVE));
            this.churchImageRepository.flush();
            this.saveDetailImages(church, param.getDetailImages().stream()
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList()));
        }

        church.update(platform, location, param.getName(), param.getAddress(), param.getHomepageURL(),
            param.getIntroduction(), param.getMinister(), param.getSchedule(), param.getPhoneNum());
    }

    @Override
    public void deleteChurch(Long churchId) {
        Church church = this.churchRepository.findById(churchId).orElse(null);

        if (church == null) {
            return;
        }

        church.delete();
    }

    private void saveDetailImages(Church church, List<String> detailImageUrls) {
        if (!detailImageUrls.isEmpty()) {
            List<ChurchImage> detailImages = detailImageUrls.stream()
                .map(url -> ChurchImage.builder()
                    .church(church)
                    .type(ChurchImage.ChurchImageType.DETAIL)
                    .url(url)
                    .status(ChurchImage.ChurchImageStatus.ACTIVE)
                    .build())
                .collect(Collectors.toList());

            this.churchImageRepository.saveAll(detailImages);
        }
    }
}
