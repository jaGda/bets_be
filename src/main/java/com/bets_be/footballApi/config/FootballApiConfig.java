package com.bets_be.footballApi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class FootballApiConfig {

    @Value("${football.api.endpoint.prod}")
    private String footballApiEndpoint;

    @Value("${football.api.host.key}")
    private String headerHostKey;

    @Value("${football.api.host.value}")
    private String headerHostValue;

    @Value("${football.api.key}")
    private String footballApiKey;

    @Value("${football.api.key.value}")
    private String footballApiValue;
}
