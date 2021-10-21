package com.bets_be.controller;

import com.bets_be.domain.CurrencyDto;
import com.bets_be.service.CurrencyConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class CurrencyConverterApiController {

    private final CurrencyConverterService service;

    @GetMapping("/currency/{from}/{to}/{amount}")
    public CurrencyDto getCurrency(@PathVariable("from") String from,
                                   @PathVariable("to") String to,
                                   @PathVariable("amount") double amount) {
        return service.fetchCurrency(from, to, amount);
    }
}
