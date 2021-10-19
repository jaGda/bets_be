package com.bets_be.controller;

import com.bets_be.domain.FixturesDto;
import com.bets_be.domain.OddsDto;
import com.bets_be.footballApi.client.FootballApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class FootballApiController {

    private final FootballApiClient footballApiClient;

    @GetMapping("/fixtures")
    public FixturesDto getFixtures() {
        return footballApiClient.getFixtures();
    }

    @GetMapping("/odds")
    public OddsDto getOdds() {
        return footballApiClient.getOdds(LocalDate.now().plusDays(4));
    }
}
