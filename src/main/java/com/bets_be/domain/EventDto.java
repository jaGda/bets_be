package com.bets_be.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EventDto {

    private Long id;
    private List<Long> couponsId;
    private Long fixtureId;
    private String fixtureDate;
    private String fixtureTime;
    private String statusLong;
    private String statusShort;
    private Long homeTeamId;
    private String homeTeamName;
    private Long awayTeamId;
    private String awayTeamName;
    private int homeGoals;
    private int awayGoals;
    private String betValue;
    private double odd;
    private boolean isWin;
}
