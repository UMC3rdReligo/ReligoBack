package com.umcreligo.umcback.domain.event;

import com.umcreligo.umcback.domain.event.domain.Event;
import com.umcreligo.umcback.domain.event.dto.CreateEventImageRes;
import com.umcreligo.umcback.domain.event.dto.CreateEventRequestDto;
import com.umcreligo.umcback.domain.event.dto.EventsRes;
import com.umcreligo.umcback.domain.event.service.EventService;
import com.umcreligo.umcback.global.config.BaseResponse;
import com.umcreligo.umcback.global.config.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<BaseResponse> createEvent(@RequestBody CreateEventRequestDto createEventDto) {
        try{
            eventService.createEvent(createEventDto);
            return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }

    @PostMapping("/image")
    public ResponseEntity<BaseResponse> createImage(@RequestBody CreateEventImageRes createEventImageRes){
        eventService.createEventImage(createEventImageRes);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<BaseResponse<EventsRes.EventInfo>> getEvent(@PathVariable Long eventId){
        try {
            EventsRes.EventInfo eventInfo = eventService.getEvent(eventId);
            return ResponseEntity.ok(new BaseResponse<>(eventInfo));
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }

    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable long eventId) {
        eventService.deleteEvent(eventId);
    }


    @GetMapping("/all")
    public ResponseEntity<BaseResponse<EventsRes>> allEvent(){
        try{
            EventsRes eventRes = eventService.getEvents(10);
            return ResponseEntity.ok(new BaseResponse<>(eventRes));
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }

    @GetMapping("/bychurch")
    public ResponseEntity<BaseResponse<EventsRes>> churchEvent(){
        try{
            EventsRes eventRes = eventService.findByChurchId(10);
            return ResponseEntity.ok(new BaseResponse<>(eventRes));
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }
}
