package com.umcreligo.umcback.domain.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateEventRequestDto {

    private String eventName;

    private LocalDateTime eventDate;

    private String eventIntroduction;

    private long churchId;

    private String participation; // 참여대상

    private String location;
}

