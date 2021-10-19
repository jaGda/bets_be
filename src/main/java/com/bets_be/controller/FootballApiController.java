package com.bets_be.controller;

import com.bets_be.domain.FixturesDto;
import com.bets_be.footballApi.client.FootballApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fixtures")
public class FootballApiController {

    private final FootballApiClient footballApiClient;

    @GetMapping
    public FixturesDto getFixtures() {
        return footballApiClient.getFixtures();
    }
}
