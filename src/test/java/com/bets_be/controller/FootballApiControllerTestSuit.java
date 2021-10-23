package com.bets_be.controller;

import com.bets_be.domain.*;
import com.bets_be.service.FootballApiService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(FootballApiController.class)
class FootballApiControllerTestSuit {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FootballApiService service;

    @Test
    void shouldGetFixtures() throws Exception {
        //Given
        List<FixtureDto> fixtureDtoList = List.of(
                new FixtureDto(
                        new FixtureDetailsDto(73561L, "2021-10-20", new StatusDto("long", "short")),
                        new TeamsDto(new HomeDto(212923L, "home"), new AwayDto(2646328L, "away")),
                        new GoalsDto(3, 0)),
                new FixtureDto(
                        new FixtureDetailsDto(1243L, "2021-10-21", new StatusDto("long", "short")),
                        new TeamsDto(new HomeDto(912832L, "home"), new AwayDto(4878390L, "away")),
                        new GoalsDto(2, 3))
        );

        when(service.fetchFixtures()).thenReturn(fixtureDtoList);
        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/fixtures")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fixture.id", Matchers.is(73561)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].fixture.status.long", Matchers.is("long")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].teams.home.id", Matchers.is(212923)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].teams.away.id", Matchers.is(4878390)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].goals.home", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].goals.away", Matchers.is(3)));
    }

    @Test
    void shouldGetOdds() throws Exception {
        //Given
        List<OddDto> oddDtoList = List.of(
                new OddDto(new FixtureIdDto(923923L), List.of(new BookmakerDto("10Bet",
                        List.of(new BetsDto("Match Winner", List.of(new BetValueDto("Home", "2.20"))))
                ))),
                new OddDto(new FixtureIdDto(234212L), List.of(new BookmakerDto("WinWin",
                        List.of(new BetsDto("Match Looser", List.of(new BetValueDto("Away", "31.70"))))
                ))));

        when(service.fetchOdds(LocalDate.now())).thenReturn(oddDtoList);
        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/odds")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fixture.id", Matchers.is(923923)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].fixture.id", Matchers.is(234212)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bookmakers[0].name", Matchers.is("10Bet")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].bookmakers[0].name", Matchers.is("WinWin")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bookmakers[0].bets[0].name", Matchers.is("Match Winner")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].bookmakers[0].bets[0].name", Matchers.is("Match Looser")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bookmakers[0].bets[0].values[0].value", Matchers.is("Home")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bookmakers[0].bets[0].values[0].odd", Matchers.is("2.20")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].bookmakers[0].bets[0].values[0].value", Matchers.is("Away")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].bookmakers[0].bets[0].values[0].odd", Matchers.is("31.70")));
    }
}
