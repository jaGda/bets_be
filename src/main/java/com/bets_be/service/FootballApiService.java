package com.bets_be.service;

import com.bets_be.domain.FixtureDto;
import com.bets_be.domain.OddDto;
import com.bets_be.footballApi.client.FootballApiClient;
import com.bets_be.loggerInfo.LoggerMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FootballApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FootballApiService.class);
    private final FootballApiClient client;

    public List<FixtureDto> fetchFixtures() {
        LOGGER.info(LoggerMessage.FETCH.getMessage("fetchFixtures"));
        return client.getFixtures().getResponse();
    }

    public List<OddDto> fetchOdds(LocalDate localDate) {
        LOGGER.info(LoggerMessage.FETCH.getMessage("fetchOdds"));
        return client.getOdds(localDate).getResponse();
    }
}
