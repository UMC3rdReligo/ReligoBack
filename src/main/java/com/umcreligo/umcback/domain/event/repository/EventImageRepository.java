package com.umcreligo.umcback.domain.event.repository;

import com.umcreligo.umcback.domain.event.domain.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventImageRepository extends JpaRepository<EventImage,Long> {

    List<EventImage> findAllByEventId(Long eventId);
}
