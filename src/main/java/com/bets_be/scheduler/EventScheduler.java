package com.bets_be.scheduler;

import com.bets_be.facade.EventSchedulerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventScheduler {

    private final EventSchedulerFacade facade;

    @Scheduled(cron = "0 0/10 12-22 * * *")
    public void updateFixtures() {
        facade.fetchFixturesAndUpdateDB();
    }

    @Scheduled(cron = "0 0 11 * * *")
    public void updateOdds() {
        facade.fetchOddsAndUpdateDB();
    }
}
