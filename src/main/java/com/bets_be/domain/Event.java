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
@Table(name = "EVENTS")
public class Event {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID", unique = true)
    private Long id;

    @ManyToMany(
            mappedBy = "eventList",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Coupon> couponList = new ArrayList<>();

    public void addCoupon(Coupon coupon) {
        couponList.add(coupon);
        coupon.getEventList().add(this);
    }
}
