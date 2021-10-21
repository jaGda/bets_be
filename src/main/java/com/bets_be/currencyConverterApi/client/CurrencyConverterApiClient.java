package com.bets_be.currencyConverterApi.client;

import com.bets_be.currencyConverterApi.config.CurrencyConverterApiConfig;
import com.bets_be.domain.CurrencyDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class CurrencyConverterApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConverterApiClient.class);

    private final RestTemplate restTemplate;
    private final CurrencyConverterApiConfig config;

    public CurrencyDto getCurrency(String from, String to, double amount) {
        URI url = UriComponentsBuilder.fromHttpUrl(config.getCurrencyConverterApiEndpoint())
                .queryParam("format", "json")
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("amount", amount)
                .build().encode().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set(config.getHeaderHostKey(), config.getHeaderHostValue());
        headers.set(config.getCurrencyConverterApiKey(), config.getCurrencyConverterApiValue());

        HttpEntity<CurrencyDto> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<CurrencyDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    CurrencyDto.class
            );
            return response.getBody();
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new CurrencyDto();
        }
    }
}
