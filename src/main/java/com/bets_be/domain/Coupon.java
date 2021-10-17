package com.bets_be.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "COUPONS")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @NotNull
    @Column(name = "ID", unique = true)
    private Long id;

    @ManyToOne(
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToMany(
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "JOIN_COUPON_EVENT",
            joinColumns = {@JoinColumn(name = "COUPON_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "EVENT_ID", referencedColumnName = "ID")}
    )
    private List<Event> eventList = new ArrayList<>();

    public Coupon(User user) {
        this.user = user;
    }
}
