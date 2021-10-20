package com.bets_be.currencyConverterApi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CurrencyConverterApiConfig {

    @Value("${currency_converter.api.prod}")
    private String currencyConverterApiEndpoint;

    @Value("${currency_converter.api.host.key}")
    private String headerHostKey;

    @Value("${currency_converter.api.host.value}")
    private String headerHostValue;

    @Value("${currency_converter.api.key}")
    private String currencyConverterApiKey;

    @Value("${currency_converter.api.key.value}")
    private String currencyConverterApiValue;
}
