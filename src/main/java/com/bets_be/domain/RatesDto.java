package com.bets_be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RatesDto {

    @JsonProperty("PLN")
    private CurrencyNameDto currencyPlnName;

    @JsonProperty("USD")
    private CurrencyNameDto currencyUsaName;

    @JsonProperty("EUR")
    private CurrencyNameDto currencyEurName;
}