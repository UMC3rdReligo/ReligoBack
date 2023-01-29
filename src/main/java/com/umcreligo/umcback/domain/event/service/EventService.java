package com.umcreligo.umcback.domain.event.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.umcreligo.umcback.domain.event.domain.Event;
import com.umcreligo.umcback.domain.event.dto.CreateEventRequestDto;
import com.umcreligo.umcback.domain.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

//    private final EventRepositoryImpl eventRepository;
    private final EventRepository eventRepository;

    public void createEvent(CreateEventRequestDto createEventDto) {
        Event event = new Event(createEventDto.getEventName(), createEventDto.getEventDate(), createEventDto.getEventIntroduction());
        eventRepository.save(event);
    }

    public Event getEvent(long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 이벤트입니다."));
        return event;
    }

    public void deleteEvent(long id) {
        eventRepository.deleteById(id);
    }

}
