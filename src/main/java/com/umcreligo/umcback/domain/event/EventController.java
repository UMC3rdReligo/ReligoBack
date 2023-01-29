package com.umcreligo.umcback.domain.event;

import com.umcreligo.umcback.domain.event.dto.CreateEventRequestDto;
import com.umcreligo.umcback.domain.event.dto.GetEventResponseDto;
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

    @GetMapping("/{id}")
    public GetEventResponseDto getEvent(@PathVariable long id) {
        return new GetEventResponseDto(eventService.getEvent(id));
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable long id) {
        eventService.deleteEvent(id);
    }
}
