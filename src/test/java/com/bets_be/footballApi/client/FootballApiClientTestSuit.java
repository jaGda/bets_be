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
    void shouldFetchFixturesDto() throws URISyntaxException {
        //Given
        FixturesDto fixturesDto = createFixturesDtoObject();

        when(footballApiConfig.getFootballApiEndpoint()).thenReturn("http://test.com");
        URI url = new URI("http://test.com/fixtures?league=106&season=2021&timezone=Europe/Warsaw&date=2021-10-18");

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
        return new FixturesDto(List.of(
                new FixtureDto(
                        new FixtureDetailsDto(1L, "2021-10-18", new StatusDto("long", "short")),
                        new TeamsDto(new HomeDto(1L, "home"), new AwayDto(2L, "away")),
                        new GoalsDto(1, 0)
                )
        ));
    }
}