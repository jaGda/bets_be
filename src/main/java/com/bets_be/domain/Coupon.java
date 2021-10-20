package com.bets_be.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@Table(name = "COUPONS")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ID", unique = true)
    private Long id;

    @ManyToOne(
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToMany(
            cascade = {CascadeType.PERSIST,CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "JOIN_COUPON_EVENT",
            joinColumns = {@JoinColumn(name = "COUPON_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "EVENT_ID", referencedColumnName = "ID")}
    )
    private List<Event> eventList = new ArrayList<>();

    private double stake;
    private double winnings;
    private LocalDate betDate;
    private LocalTime betTime;
    private boolean isVictory;

    public Coupon(User user) {
        this.user = user;
    }
}
