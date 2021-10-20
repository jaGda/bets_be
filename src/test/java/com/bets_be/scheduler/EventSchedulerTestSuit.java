package com.bets_be.scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EventSchedulerTestSuit {

    @Autowired
    private EventScheduler eventScheduler;

    @Test
    void test() {
        eventScheduler.updateFixtures();
        eventScheduler.updateOdds();
    }
}