package com.umcreligo.umcback.domain.church.repository;

import com.umcreligo.umcback.domain.church.domain.ChurchImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChurchImageRepository extends JpaRepository<ChurchImage, Long> {
    List<ChurchImage> findAllByChurchIdAndTypeAndStatus(Long churchId, ChurchImage.ChurchImageType churchImageType,
                                                        ChurchImage.ChurchImageStatus churchImageStatus);

    List<ChurchImage> findAllByChurchIdInAndTypeAndStatus(List<Long> churchId, ChurchImage.ChurchImageType churchImageType,
                                                          ChurchImage.ChurchImageStatus churchImageStatus);
}
