package com.bets_be.service;

import com.bets_be.currencyConverterApi.client.CurrencyConverterApiClient;
import com.bets_be.domain.CurrencyDto;
import com.bets_be.loggerInfo.LoggerMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyConverterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConverterService.class);
    private final CurrencyConverterApiClient client;

    public CurrencyDto fetchCurrency(String from, String to, double amount) {
        LOGGER.info(LoggerMessage.FETCH.getMessage("fetchCurrency"));
        return client.getCurrency(from, to, amount);
    }
}
