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
public class FixtureDetailsDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("date")
    private String date;

    @JsonProperty("status")
    private StatusDto statusDto;
}
