package com.umcreligo.umcback.domain.event.repository;

import com.umcreligo.umcback.domain.event.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
