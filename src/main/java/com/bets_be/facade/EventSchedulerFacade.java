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
import java.util.List;
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
            List<Event> eventList = eventService.findEventsByFixtureId(fixtureDto.getFixtureDetailsDto().getId());
            if (eventList.isEmpty()) {
                Stream.of("Home", "Draw", "Away")
                        .forEach(s -> {
                            eventService.addEvent(eventMapper.mapToEvent(fixtureDto, s));
                            LOGGER.info("Football fixture has been created and added to the DB...");
                        });
            } else {
                eventList.forEach(event -> {
                    eventService.updateEvent(eventMapper.mapToEvent(event, fixtureDto));
                    LOGGER.info("Football fixture has been updated");
                });
            }
        });
    }

    public void fetchOddsAndUpdateDB() {
        Stream.iterate(0, n -> n + 1).limit(7)
                .map(i -> LocalDate.now().plusDays(i))
                .forEach(localDate -> {
                    footballApiService.fetchOdds(localDate).forEach(oddDto -> {
                        List<Event> eventList = eventService.findEventsByFixtureId(oddDto.getFixtureIdDto().getId());
                        if (!eventList.isEmpty()) {
                            eventList.forEach(event -> {
                                oddDto.getBookmakerDtoList().get(0).getBetsDtoList().get(0).getBetValueDtoList()
                                        .forEach(betValueDto -> {
                                            if (betValueDto.getValue().equals(event.getBetValue())) {
                                                event.setOdd(Double.parseDouble(betValueDto.getOdd()));
                                                eventService.updateEvent(event);
                                                LOGGER.info("Betting odd has been updated...");
                                            }
                                        });
                            });
                        } else {
                            LOGGER.info("Betting odds aren't updating because indicated fixture doesn't exist in the DB...");
                        }
                    });
                });
    }
}
