package com.bets_be.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class CouponDto {

    private Long id;
    private Long userId;
    private List<Long> eventsId;
    private double stake;
    private double winnings;
    private LocalDate betDate;
    private LocalTime betTime;
    private boolean isVictory;
}
