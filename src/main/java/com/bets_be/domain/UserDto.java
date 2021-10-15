package com.bets_be.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UserDto {

    private Long id;
    private String name;
    private String login;
    private String mail;
    private List<Long> couponsId;
}
