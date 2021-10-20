package com.bets_be.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "EVENTS")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ID", unique = true)
    private Long id;

    @ManyToMany(
            mappedBy = "eventList",
            fetch = FetchType.EAGER
    )
    private List<Coupon> couponList = new ArrayList<>();

    private Long fixtureId;
    private LocalDate fixtureDate;
    private LocalTime fixtureTime;
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

    public void addCoupon(Coupon coupon) {
        couponList.add(coupon);
        coupon.getEventList().add(this);
    }
}
