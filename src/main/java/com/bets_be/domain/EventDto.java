package com.bets_be.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class EventDto {

    private Long id;
    private List<Long> couponsId = new ArrayList<>();
}
