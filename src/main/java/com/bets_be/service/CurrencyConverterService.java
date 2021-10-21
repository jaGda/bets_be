package com.bets_be.service;

import com.bets_be.currencyConverterApi.client.CurrencyConverterApiClient;
import com.bets_be.domain.CurrencyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyConverterService {

    private final CurrencyConverterApiClient client;

    public CurrencyDto fetchCurrency(String from, String to, double amount) {
        return client.getCurrency(from, to, amount);
    }
}
