package com.bets_be.controller;

import com.bets_be.domain.FixtureDto;
import com.bets_be.domain.OddDto;
import com.bets_be.service.FootballApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class FootballApiController {

    private final FootballApiService service;

    @GetMapping("/fixtures")
    public List<FixtureDto> getFixtures() {
        return service.fetchFixtures();
    }

    @GetMapping("/odds")
    public List<OddDto> getOdds() {
        return service.fetchOdds(LocalDate.now());
    }
}
