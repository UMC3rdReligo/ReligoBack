package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.domain.Church;
import com.umcreligo.umcback.domain.church.domain.ChurchHashTag;
import com.umcreligo.umcback.domain.church.domain.ChurchImage;
import com.umcreligo.umcback.domain.church.dto.FindChurchResult;
import com.umcreligo.umcback.domain.church.repository.ChurchHashTagRepository;
import com.umcreligo.umcback.domain.church.repository.ChurchImageRepository;
import com.umcreligo.umcback.domain.church.repository.ChurchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChurchProviderImpl implements ChurchProvider {
    private final ChurchRepository churchRepository;
    private final ChurchHashTagRepository churchHashTagRepository;
    private final ChurchImageRepository churchImageRepository;

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
    public List<Church> searchChurches() {
        return new ArrayList<>();
    }

    @Override
    public List<Church> recommendChurches() {
        return new ArrayList<>();
    }
}
