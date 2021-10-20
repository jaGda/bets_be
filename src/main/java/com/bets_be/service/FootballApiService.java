package com.bets_be.service;

import com.bets_be.domain.FixtureDto;
import com.bets_be.domain.OddDto;
import com.bets_be.footballApi.client.FootballApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FootballApiService {

    private final FootballApiClient client;

    public List<FixtureDto> fetchFixtures() {
        return client.getFixtures().getResponse();
    }

    public List<OddDto> fetchOdds(LocalDate localDate) {
        return client.getOdds(localDate).getResponse();
    }
}
