package com.bets_be.service;

import com.bets_be.domain.Event;
import com.bets_be.loggerInfo.LoggerMessage;
import com.bets_be.repository.EventDao;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);
    private final EventDao eventDao;

    public List<Event> getAllEvents() {
        LOGGER.info(LoggerMessage.FETCH.getMessage("getAllEvents"));
        return eventDao.findAll();
    }

    public Event getEventById(Long eventId) {
        LOGGER.info(LoggerMessage.FETCH.getMessage("getEventById"));
        return eventDao.findById(eventId).orElseThrow(
                () -> new RuntimeException("Event of id '" + eventId + "' doesn't exist")
        );
    }

    public Event addEvent(Event event) {
        LOGGER.info(LoggerMessage.CREATE.getMessage("addEvent"));
        return eventDao.save(event);
    }

    public Event updateEvent(Event event) {
        LOGGER.info(LoggerMessage.UPDATE.getMessage("updateEvent"));
        if (eventDao.findById(event.getId()).isEmpty()) {
            throw new RuntimeException("Event of id '" + event.getId() + "' doesn't exist");
        } else {
            return eventDao.save(event);
        }
    }

    public void removeEventById(Long eventId) {
        LOGGER.info(LoggerMessage.DELETE.getMessage("removeEventById"));
        eventDao.deleteById(eventId);
    }

    public List<Event> findEventsByFixtureId(Long fixtureId) {
        LOGGER.info(LoggerMessage.FETCH.getMessage("findEventsByFixtureId"));
        return eventDao.findAllByFixtureId(fixtureId);
    }

    public List<Event> findEventsBetweenDates(String from, String to) {
        LOGGER.info(LoggerMessage.FETCH.getMessage("findEventsBetweenDates"));
        return eventDao.findAllByFixtureDateBetween(LocalDate.parse(from), LocalDate.parse(to));
    }

    public List<Event> findEventsByDate(String date) {
        LOGGER.info(LoggerMessage.FETCH.getMessage("findEventsByDate"));
        return eventDao.findAllByFixtureDate(LocalDate.parse(date));
    }
}
