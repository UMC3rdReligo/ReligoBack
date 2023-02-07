package com.umcreligo.umcback.domain.event.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Event {

    @Id
    @GeneratedValue
    private long id; // pk

    private String eventName;

    private LocalDateTime eventDate;

    private String eventIntroduction;

    private String participation; // 참여대상

    private String location;

    private long churchId;

    @Builder
    public Event(String eventName, LocalDateTime eventDate, String eventIntroduction, String participation, String location, long churchId) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventIntroduction = eventIntroduction;
        this.participation = participation;
        this.location = location;
        this.churchId = churchId;
    }
}
