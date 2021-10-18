package com.bets_be.footballApi.client;

import com.bets_be.domain.FixturesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class FootballApilClient {

    private final RestTemplate restTemplate;

    public FixturesDto getFixtures() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("x-rapidapi-host", "api-football-beta.p.rapidapi.com");
        headers.set("x-rapidapi-key", "3cb77449ffmshccbbcbb119cb0d9p1a918fjsn245e8e1de959");

        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<FixturesDto> response = restTemplate.exchange(
                "https://api-football-beta.p.rapidapi.com/fixtures?league=106&season=2021&timezone=Europe/Warsaw&date=2021-10-18",
                HttpMethod.GET,
                request,
                FixturesDto.class
        );

        return response.getBody();
    }
}
