package com.umcreligo.umcback.domain.event.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventsRes {

    List<EventInfo> infos;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventInfo{
        private Long eventId;
        private List<String> eventImage;
        private String eventName;
        private String location;
        private LocalDateTime eventDate;
        private String eventIntroduction;
        private String participation; // 참여대상
    }
}
