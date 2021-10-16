package com.bets_be.controller;

import com.bets_be.domain.EventDto;
import com.bets_be.mapper.EventMapper;
import com.bets_be.repository.CouponDao;
import com.bets_be.repository.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private CouponDao couponDao;

    @GetMapping
    public List<EventDto> getEvents() {
        return eventMapper.mapToEventDtoList(eventDao.findAll());
    }

    @GetMapping("/{eventId}")
    public EventDto getEvent(@PathVariable Long eventId) {
        return eventMapper.mapToEventDto(eventDao.findById(eventId).orElseThrow(
                () -> new RuntimeException("Event of id '" + eventId + "' doesn't exist")
        ));
    }

    @PostMapping
    public EventDto createEvent(@RequestBody EventDto eventDto) {
        return eventMapper.mapToEventDto(eventDao.save(eventMapper.mapToEvent(eventDto)));
    }

    @PutMapping
    public EventDto updateEvent(@RequestBody EventDto eventDto) {
        if (eventDao.findById(eventDto.getId()).isEmpty()) {
            throw new RuntimeException("Event of id '" + eventDto.getId() + "' doesn't exist");
        } else {
            return eventMapper.mapToEventDto(eventDao.save(eventMapper.mapToEvent(eventDto)));
        }
    }

    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable Long eventId) {
        eventDao.deleteById(eventId);
    }
}
