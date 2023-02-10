package com.umcreligo.umcback.domain.event.dto;

import com.umcreligo.umcback.domain.event.domain.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EventDetails {

    private long eventId;
    private String eventName;
    private LocalDateTime eventDate;
    private String eventIntroduction;
    private long churchId;
    private String participation; // 참여대상
    private String location;
    private String description;


}
