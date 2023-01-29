package com.umcreligo.umcback.domain.event.repository;

import com.umcreligo.umcback.domain.event.domain.Event;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class EventRepositoryImpl {

    private static Map<Long, Event> events = new HashMap<>();

    public void save(Event event) {
        events.put(event.getId(), event);
    }

    public static Event getEvent(int pk) {
        Event event = events.get(pk);
        return event;
    }

    public Event deleteEvent(int pk) {
        return events.remove(pk);
    }
}
