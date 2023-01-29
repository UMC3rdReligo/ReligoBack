package com.umcreligo.umcback.domain.event.dto;

import com.umcreligo.umcback.domain.event.domain.Event;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetEventResponseDto {
    private String eventName;

    private String eventDate;

    private String eventIntroduction;

    public GetEventResponseDto(Event event) {
        this.eventName = event.getEventName();
        this.eventDate = event.getEventDate();
        this.eventIntroduction = event.getEventIntroduction();
    }
}
