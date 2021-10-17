package com.bets_be.service;

import com.bets_be.domain.Event;
import com.bets_be.repository.EventDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventDao eventDao;

    public List<Event> getAllEvents() {
        return eventDao.findAll();
    }

    public Event getEventById(Long eventId) {
        return eventDao.findById(eventId).orElseThrow(
                () -> new RuntimeException("Event of id '" + eventId + "' doesn't exist")
        );
    }

    public Event addEvent(Event event) {
        return eventDao.save(event);
    }

    public Event updateEvent(Event event) {
        if (eventDao.findById(event.getId()).isEmpty()) {
            throw new RuntimeException("Event of id '" + event.getId() + "' doesn't exist");
        } else {
            return eventDao.save(event);
        }
    }

    public void removeEventById(Long eventId) {
        eventDao.deleteById(eventId);
    }
}
