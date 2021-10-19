package com.bets_be.footballApi.client;

import com.bets_be.domain.*;
import com.bets_be.footballApi.config.FootballApiConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FootballApiClientTestSuit {

    @InjectMocks
    private FootballApiClient footballApiClient;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private FootballApiConfig footballApiConfig;

    @Test
    void shouldFetchOddsDto() throws URISyntaxException {
        //Given
        LocalDate localDate = LocalDate.now();
        OddsDto oddsDto = createOddsDtoObject();

        when(footballApiConfig.getFootballApiEndpoint()).thenReturn("http://test.com");
        URI url = new URI("http://test.com/" +
                "odds?league=106&season=2021&timezone=Europe/Warsaw&bet=1&bookmaker=1&date="
                + localDate);

        when(footballApiConfig.getHeaderHostKey()).thenReturn("host_key");
        when(footballApiConfig.getHeaderHostValue()).thenReturn("host_value");
        when(footballApiConfig.getFootballApiKey()).thenReturn("api_key");
        when(footballApiConfig.getFootballApiValue()).thenReturn("api_value");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("host_key", "host_value");
        headers.set("api_key", "api_value");

        HttpEntity<OddsDto> request = new HttpEntity<>(headers);

        ResponseEntity<OddsDto> responseEntity = new ResponseEntity<>(oddsDto, HttpStatus.OK);

        when(restTemplate.exchange(url, HttpMethod.GET, request, OddsDto.class))
                .thenReturn(responseEntity);
        //When
        OddsDto result = footballApiClient.getOdds(localDate);
        //Then
        assertAll(() -> {
            assertEquals(1, result.getResponse().size());
            assertEquals(1L, result.getResponse().get(0)
                    .getFixtureIdDto().getId());
            assertEquals("10Bet", result.getResponse().get(0)
                    .getBookmakerDtoList().get(0).getName());
            assertEquals(1, result.getResponse().get(0)
                    .getBookmakerDtoList().get(0).getBetsDtoList().size());
            assertEquals("Match Winner", result.getResponse().get(0)
                    .getBookmakerDtoList().get(0).getBetsDtoList().get(0)
                    .getName());
            assertEquals("2.70", result.getResponse().get(0)
                    .getBookmakerDtoList().get(0).getBetsDtoList().get(0)
                    .getBetValueDtoList().get(0).getOdd());
        });
    }

    private OddsDto createOddsDtoObject() {
        return new OddsDto(List.of(new OddDto(new FixtureIdDto(1L), List.of(new BookmakerDto("10Bet",
                List.of(new BetsDto("Match Winner", List.of(new BetValueDto("Home", "2.70"))))
        )))));
    }

    @Test
    void shouldFetchFixturesDto() throws URISyntaxException {
        //Given
        FixturesDto fixturesDto = createFixturesDtoObject();

        when(footballApiConfig.getFootballApiEndpoint()).thenReturn("http://test.com");
        URI url = new URI("http://test.com/fixtures?league=106&season=2021&timezone=Europe/Warsaw&from="
                + LocalDate.now() + "&to=" + LocalDate.now().plusDays(7));

        when(footballApiConfig.getHeaderHostKey()).thenReturn("host_key");
        when(footballApiConfig.getHeaderHostValue()).thenReturn("host_value");
        when(footballApiConfig.getFootballApiKey()).thenReturn("api_key");
        when(footballApiConfig.getFootballApiValue()).thenReturn("api_value");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("host_key", "host_value");
        headers.set("api_key", "api_value");

        HttpEntity<FixturesDto> request = new HttpEntity<>(headers);

        ResponseEntity<FixturesDto> responseEntity = new ResponseEntity<>(fixturesDto, HttpStatus.OK);

        when(restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                FixturesDto.class)).thenReturn(responseEntity);
        //When
        FixturesDto result = footballApiClient.getFixtures();
        //Then
        assertAll(() -> {
            assertEquals(1, result.getResponse().size());
            assertEquals("2021-10-18", result.getResponse().get(0).getFixtureDetailsDto().getDate());
            assertEquals(2L, result.getResponse().get(0).getTeamsDto().getAwayDto().getId());
            assertEquals(0, result.getResponse().get(0).getGoalsDto().getAway());
        });
    }

    public FixturesDto createFixturesDtoObject() {
        return new FixturesDto(List.of(new FixtureDto(
                new FixtureDetailsDto(1L, "2021-10-18", new StatusDto("long", "short")),
                new TeamsDto(new HomeDto(1L, "home"), new AwayDto(2L, "away")),
                new GoalsDto(1, 0)
        )));
    }
}