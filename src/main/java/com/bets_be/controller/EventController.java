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

    @GetMapping("/fixture/{fixtureId}")
    public List<EventDto> getEventsByFixtureId(@PathVariable Long fixtureId) {
        return mapper.mapToEventDtoList(service.findEventsByFixtureId(fixtureId));
    }

    @GetMapping("/date/{date}")
    public List<EventDto> getEventsByDate(@PathVariable String date) {
        return mapper.mapToEventDtoList(service.findEventsByDate(date));
    }

    @GetMapping("/{from}/{to}")
    public List<EventDto> getEventsByDateBetween(@PathVariable("from") String from,
                                                 @PathVariable("to") String to) {
        return mapper.mapToEventDtoList(service.findEventsBetweenDates(from, to));
    }
}
