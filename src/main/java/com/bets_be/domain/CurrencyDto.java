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
public class CurrencyDto {
    public static final String PLN = "PLN";
    public static final String EUR = "EUR";
    public static final String USD = "USD";

    @JsonProperty("base_currency_code")
    private String base_currency_code;

    @JsonProperty("base_currency_name")
    private String base_currency_name;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("updated_date")
    private String updated_date;

    @JsonProperty("rates")
    private RatesDto rates;
}
