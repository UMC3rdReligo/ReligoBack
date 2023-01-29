package com.umcreligo.umcback.domain.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateEventRequestDto {

    private String eventName;
    private String eventDate;
    private String eventIntroduction;
}

