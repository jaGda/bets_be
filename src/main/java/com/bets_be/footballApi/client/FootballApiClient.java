package com.bets_be.footballApi.client;

import com.bets_be.domain.FixturesDto;
import com.bets_be.footballApi.config.FootballApiConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class FootballApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(FootballApiClient.class);

    private final RestTemplate restTemplate;
    private final FootballApiConfig footballApiConfig;

    public FixturesDto getFixtures() {
        URI url = UriComponentsBuilder.fromHttpUrl(footballApiConfig.getFootballApiEndpoint() + "/fixtures")
                .queryParam("league", 106)
                .queryParam("season", 2021)
                .queryParam("timezone", "Europe/Warsaw")
                .queryParam("date", "2021-10-18")
                .build().encode().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set(footballApiConfig.getHeaderHostKey(), footballApiConfig.getHeaderHostValue());
        headers.set(footballApiConfig.getFootballApiKey(), footballApiConfig.getFootballApiValue());

        HttpEntity<FixturesDto> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<FixturesDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    FixturesDto.class
            );
            return response.getBody();
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new FixturesDto();
        }
    }
}
