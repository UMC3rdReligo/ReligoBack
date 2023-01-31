package com.umcreligo.umcback.domain.event.repository;

import com.umcreligo.umcback.domain.event.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByChurchId(long churchId);
}
