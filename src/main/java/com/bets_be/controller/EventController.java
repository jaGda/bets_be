package com.bets_be.controller;

import com.bets_be.domain.EventDto;
import com.bets_be.mapper.EventMapper;
import com.bets_be.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService service;
    private final EventMapper mapper;

    @GetMapping
    public List<EventDto> getEvents() {
        return mapper.mapToEventDtoList(service.getAllEvents());
    }

    @GetMapping("/{eventId}")
    public EventDto getEvent(@PathVariable Long eventId) {
        return mapper.mapToEventDto(service.getEventById(eventId));
    }

    @PostMapping
    public EventDto createEvent(@RequestBody EventDto eventDto) {
        return mapper.mapToEventDto(service.addEvent(mapper.mapToEvent(eventDto)));
    }

    @PutMapping
    public EventDto updateEvent(@RequestBody EventDto eventDto) {
        return mapper.mapToEventDto(service.updateEvent(mapper.mapToEvent(eventDto)));
    }

    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable Long eventId) {
        service.removeEventById(eventId);
    }
}
