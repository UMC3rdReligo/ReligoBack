package com.umcreligo.umcback.domain.event;

import com.umcreligo.umcback.domain.event.domain.Event;
import com.umcreligo.umcback.domain.event.dto.CreateEventRequestDto;
import com.umcreligo.umcback.domain.event.dto.EventsRes;
import com.umcreligo.umcback.domain.event.dto.GetEventByChurchIdResponseDto;
import com.umcreligo.umcback.domain.event.dto.GetEventByIdResponseDto;
import com.umcreligo.umcback.domain.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @PostMapping
    public void createEvent(@RequestBody CreateEventRequestDto createEventDto) {
        eventService.createEvent(createEventDto);
    }

    @GetMapping("/eventId/{id}")
    public GetEventByIdResponseDto getEvent(@PathVariable long id) {
        return new GetEventByIdResponseDto(eventService.getEvent(id));
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable long id) {
        eventService.deleteEvent(id);
    }

    @GetMapping("/churchId/{churchId}")
    public GetEventByChurchIdResponseDto findByChurchId(@PathVariable long churchId) {
        return new GetEventByChurchIdResponseDto(eventService.findByChurchId(churchId));
    }

    @GetMapping("/event/all")
    public EventsRes allEvent(){
        return new EventsRes(eventService.getEvents());
    }
}
