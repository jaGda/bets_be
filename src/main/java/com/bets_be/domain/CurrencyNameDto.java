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
public class CurrencyNameDto {

    @JsonProperty("currency_name")
    private String currencyName;

    @JsonProperty("rate")
    private String rate;

    @JsonProperty("rate_for_amount")
    private String rateForAmount;
}
