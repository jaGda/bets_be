package com.bets_be.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CouponDto {

    private Long id;
    private Long userId;
    private List<Long> eventsId;
    private String betCurrency;
    private double stake;
    private double winningsPLN;
    private double winningsUSA;
    private double winningsEUR;
    private String betDate;
    private String betTime;
    private boolean isVictory;
}
