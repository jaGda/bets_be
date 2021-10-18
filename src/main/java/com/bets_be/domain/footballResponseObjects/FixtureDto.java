package com.bets_be.domain.footballResponseObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixtureDto {

    @JsonProperty("fixture")
    private FixtureDetailsDto fixtureDetailsDto;

    @JsonProperty("teams")
    private TeamsDto teamsDto;

    @JsonProperty("goals")
    private GoalsDto goalsDto;
}
