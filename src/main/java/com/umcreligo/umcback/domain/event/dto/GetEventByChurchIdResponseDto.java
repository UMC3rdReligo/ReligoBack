package com.umcreligo.umcback.domain.event.dto;

import com.umcreligo.umcback.domain.event.domain.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetEventByChurchIdResponseDto {
    List<EventDetails> eventDetails = new ArrayList<>();

    public GetEventByChurchIdResponseDto(List<Event> events) {
        events.stream().forEach(e ->
            eventDetails.add(new EventDetails(e))
        );
    }
}
