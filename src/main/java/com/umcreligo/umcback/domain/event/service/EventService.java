package com.umcreligo.umcback.domain.event.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.umcreligo.umcback.domain.event.domain.Event;
import com.umcreligo.umcback.domain.event.dto.CreateEventRequestDto;
import com.umcreligo.umcback.domain.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

//    private final EventRepositoryImpl eventRepository;
    private final EventRepository eventRepository;

    public void createEvent(CreateEventRequestDto createEventDto) {
        Event event = Event.builder()
            .eventName(createEventDto.getEventName())
            .eventDate(createEventDto.getEventDate())
            .eventIntroduction(createEventDto.getEventIntroduction())
            .participation(createEventDto.getParticipation())
            .location(createEventDto.getLocation())
            .churchId(createEventDto.getChurchId()).build();

        eventRepository.save(event);
        // SQL 공부하도록
        // insert event () ...
    }

    public Event getEvent(long id) {
        //SQL 공부하도록/ 나의 장염걸린 시간을 맞바
        // select * from event where id = 1;
        Event event = eventRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 이벤트입니다."));
        return event;
    }

    public void deleteEvent(long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> findByChurchId(long churchId) {
        return eventRepository.findByChurchId(churchId);
    }
}
