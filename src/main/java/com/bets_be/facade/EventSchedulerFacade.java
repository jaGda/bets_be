package com.bets_be.facade;

import com.bets_be.domain.Event;
import com.bets_be.mapper.EventMapper;
import com.bets_be.scheduler.EventScheduler;
import com.bets_be.service.EventService;
import com.bets_be.service.FootballApiService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class EventSchedulerFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventScheduler.class);

    private final FootballApiService footballApiService;
    private final EventService eventService;
    private final EventMapper eventMapper;

    public void fetchFixturesAndUpdateDB() {
        footballApiService.fetchFixtures().forEach(fixtureDto -> {
            if (eventService.findEventByFixtureId(fixtureDto.getFixtureDetailsDto().getId()).isPresent()) {
                eventService.updateEvent(eventMapper.mapToEvent(eventService.findEventByFixtureId(fixtureDto.getFixtureDetailsDto().getId()).get(), fixtureDto));
            } else {
                eventService.addEvent(eventMapper.mapToEvent(fixtureDto));
            }
            LOGGER.info("Football fixtures are updating...");
        });
    }

    public void fetchOddsAndUpdateDB() {
        Stream.iterate(0, n -> n + 1).limit(7)
                .map(i -> LocalDate.now().plusDays(i))
                .forEach(localDate -> {
                    footballApiService.fetchOdds(localDate).forEach(oddDto -> {
                        Optional<Event> optionalEvent = eventService.findEventByFixtureId(oddDto.getFixtureIdDto().getId());
                        optionalEvent.ifPresent(event -> eventService.updateEvent(eventMapper.mapToEvent(event, oddDto)));
                        LOGGER.info("Betting odds are updating...");
                    });
                });
    }
}
