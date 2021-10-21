package com.bets_be.builder;

import com.bets_be.domain.Event;
import com.bets_be.domain.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CouponBuilder {
    private final com.bets_be.domain.Coupon coupon;

    public static class Coupon {
        private Long id;
        private User user;
        private final List<Event> eventList = new ArrayList<>();
        private String betCurrency;
        private double stake;
        private double winningsPLN;
        private double winningsUSA;
        private double winningsEUR;
        private LocalDate betDate;
        private LocalTime betTime;
        private boolean isVictory;

        public Coupon id(Long id) {
            this.id = id;
            return this;
        }

        public Coupon user(User user) {
            this.user = user;
            return this;
        }

        public Coupon eventList(List<Event> eventList) {
            this.eventList.addAll(eventList);
            return this;
        }

        public Coupon betCurrency(String betCurrency) {
            this.betCurrency = betCurrency;
            return this;
        }

        public Coupon stake(double stake) {
            this.stake = stake;
            return this;
        }

        public Coupon winningsPLN(double winningsPLN) {
            this.winningsPLN = winningsPLN;
            return this;
        }

        public Coupon winningsUSA(double winningsUSA) {
            this.winningsUSA = winningsUSA;
            return this;
        }

        public Coupon winningsEUR(double winningsEUR) {
            this.winningsEUR = winningsEUR;
            return this;
        }

        public Coupon betDate(LocalDate betDate) {
            this.betDate = betDate;
            return this;
        }

        public Coupon betTime(LocalTime betTime) {
            this.betTime = betTime;
            return this;
        }

        public Coupon isVictory(boolean isVictory) {
            this.isVictory = isVictory;
            return this;
        }

        public CouponBuilder build() {
            return new CouponBuilder(id, user, eventList, betCurrency, stake,
                    winningsPLN, winningsUSA, winningsEUR, betDate, betTime, isVictory);
        }
    }

    public CouponBuilder(Long id, User user, List<Event> eventList,
                         String betCurrency, double stake, double winningsPLN,
                         double winningsUSA, double winningsEUR, LocalDate betDate,
                         LocalTime betTime, boolean isVictory) {
        this.coupon = new com.bets_be.domain.Coupon(id, user,
                eventList, betCurrency, stake, winningsPLN,
                winningsUSA, winningsEUR, betDate, betTime, isVictory);
    }

    public com.bets_be.domain.Coupon getCoupon() {
        return coupon;
    }
}
