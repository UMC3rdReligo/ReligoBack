package com.umcreligo.umcback.domain.event.dto;


import com.umcreligo.umcback.domain.event.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EventsRes {

    List<Event> infos;
}
