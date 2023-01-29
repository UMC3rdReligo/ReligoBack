package com.umcreligo.umcback.domain.event.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue
    private long id; // pk

    private String eventName;

    private String eventDate;

    private String eventIntroduction;

    public Event(String eventName, String eventDate, String eventIntroduction) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventIntroduction = eventIntroduction;
    }
}
