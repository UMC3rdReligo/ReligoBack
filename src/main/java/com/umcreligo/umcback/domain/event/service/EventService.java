package com.umcreligo.umcback.domain.event.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.umcreligo.umcback.domain.church.domain.Church;
import com.umcreligo.umcback.domain.church.repository.ChurchRepository;
import com.umcreligo.umcback.domain.event.domain.Event;
import com.umcreligo.umcback.domain.event.domain.EventImage;
import com.umcreligo.umcback.domain.event.dto.CreateEventImageRes;
import com.umcreligo.umcback.domain.event.dto.CreateEventRequestDto;
import com.umcreligo.umcback.domain.event.dto.EventsRes;
import com.umcreligo.umcback.domain.event.repository.EventImageRepository;
import com.umcreligo.umcback.domain.event.repository.EventRepository;
import com.umcreligo.umcback.domain.user.domain.User;
import com.umcreligo.umcback.domain.user.repository.UserRepository;
import com.umcreligo.umcback.domain.user.service.UserService;
import com.umcreligo.umcback.global.config.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

//    private final EventRepositoryImpl eventRepository;
    private final EventRepository eventRepository;

    private final ChurchRepository churchRepository;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final EventImageRepository eventImageRepository;

    public void createEvent(CreateEventRequestDto createEventDto) {
        Church church = churchRepository.findById(createEventDto.getChurchId()).orElseThrow();
        Event event = Event.builder()
            .eventName(createEventDto.getEventName())
            .eventDate(createEventDto.getEventDate())
            .eventIntroduction(createEventDto.getEventIntroduction())
            .participation(createEventDto.getParticipation())
            .location(church.getAddress())
            .church(church).build();

        eventRepository.save(event);
    }

    public void createEventImage(CreateEventImageRes createEventImageRes){
        Event event = eventRepository.findById(createEventImageRes.getId()).orElseThrow();
        EventImage eventimage = new EventImage();
        eventimage.setImageURL(createEventImageRes.getImageUrl());
        eventimage.setEvent(event);
        eventimage.setImageType(EventImage.ImageType.MAIN);
        eventImageRepository.save(eventimage);
    }

    public EventsRes.EventInfo getEvent(Long eventId) throws NoSuchElementException{
        Event event = eventRepository.findById(eventId).orElseThrow();
        EventsRes.EventInfo eventInfo = new EventsRes.EventInfo();
        List<EventImage> eventImage = (List<EventImage>) eventImageRepository.findAllByEventId(eventId);
        List<String> eventImageUrl = new ArrayList<>();
        for(int i=0;i<eventImage.size();i++){
            eventImageUrl.add(eventImage.get(i).getImageURL());
        }
        eventInfo.setEventId(event.getId());
        eventInfo.setEventImage(eventImageUrl);
        eventInfo.setEventName(event.getEventName()==null ? "": event.getEventName());
        eventInfo.setLocation(event.getLocation()==null ? "": event.getLocation());
        eventInfo.setEventDate(event.getEventDate()==null ? LocalDateTime.MAX: event.getEventDate());
        eventInfo.setEventIntroduction(event.getEventIntroduction()==null ? "": event.getEventIntroduction());
        eventInfo.setParticipation(event.getParticipation()==null ? "": event.getParticipation());
        return eventInfo;
    }
    public EventsRes getEvents(int pageSize) throws NoSuchElementException {
        Page<Event> events = eventRepository.findAll(Pageable.ofSize(pageSize));
        List<EventsRes.EventInfo> eventInfos = events.stream().map(event -> {
            EventsRes.EventInfo eventInfo = new EventsRes.EventInfo();
            List<EventImage> eventImage = (List<EventImage>) eventImageRepository.findAllByEventId(event.getId());
            List<String> eventImageUrl = new ArrayList<>();
            for(int i=0;i<eventImage.size();i++){
                eventImageUrl.add(eventImage.get(i).getImageURL());
            }
            eventInfo.setEventId(event.getId());
            eventInfo.setEventImage(eventImageUrl);
            eventInfo.setEventName(event.getEventName()==null ? "": event.getEventName());
            eventInfo.setLocation(event.getLocation()==null ? "": event.getLocation());
            eventInfo.setEventDate(event.getEventDate()==null ? LocalDateTime.MAX: event.getEventDate());
            eventInfo.setEventIntroduction(event.getEventIntroduction()==null ? "": event.getEventIntroduction());
            eventInfo.setParticipation(event.getParticipation()==null ? "": event.getParticipation());
            return eventInfo;
        }).collect(Collectors.toList());
        return new EventsRes(eventInfos);
    }

    public Event getEvent(long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 이벤트입니다."));
        return event;
    }

    public void deleteEvent(long id) {
        eventRepository.deleteById(id);
    }

    public EventsRes findByChurchId(int pageSize) throws NoSuchElementException {
        User user = userRepository.findWithJoinByIdAndStatus(jwtService.getId(), User.UserStatus.ACTIVE).orElseThrow();
        List<Event> events = eventRepository.findAllByChurchId(user.getChurch().getId(),Pageable.ofSize(pageSize));
        List<EventsRes.EventInfo> eventInfos = events.stream().map(event -> {
            EventsRes.EventInfo eventInfo = new EventsRes.EventInfo();
            List<EventImage> eventImage = (List<EventImage>) eventImageRepository.findAllByEventId(event.getId());
            List<String> eventImageUrl = new ArrayList<>();
            for(int i=0;i<eventImage.size();i++){
                eventImageUrl.add(eventImage.get(i).getImageURL());
            }
            eventInfo.setEventId(event.getId());
            eventInfo.setEventImage(eventImageUrl);
            eventInfo.setEventName(event.getEventName()==null ? "": event.getEventName());
            eventInfo.setLocation(event.getLocation()==null ? "": event.getLocation());
            eventInfo.setEventDate(event.getEventDate()==null ? LocalDateTime.MAX: event.getEventDate());
            eventInfo.setEventIntroduction(event.getEventIntroduction()==null ? "": event.getEventIntroduction());
            eventInfo.setParticipation(event.getParticipation()==null ? "": event.getParticipation());
            return eventInfo;
        }).collect(Collectors.toList());
        return new EventsRes(eventInfos);
    }


}
