package com.bets_be.repository;

import com.bets_be.domain.Coupon;
import com.bets_be.domain.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class EventRepositoryTestSuit {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    void testSaveManyToMany() {
        //Given
        Coupon coupon1 = new Coupon();
        Coupon coupon2 = new Coupon();
        Coupon coupon3 = new Coupon();
        Event event1 = new Event();
        Event event2 = new Event();
        Event event3 = new Event();
        eventRepository.saveAll(List.of(event1, event2, event3));
        event1.addCoupon(coupon1);
        event1.addCoupon(coupon2);
        event1.addCoupon(coupon3);
        event2.addCoupon(coupon2);
        event2.addCoupon(coupon3);
        event3.addCoupon(coupon2);
        //When
        couponRepository.saveAll(List.of(coupon1, coupon2, coupon3));
        //Then
        assertAll(() -> {
            assertNotEquals(0, couponRepository.findAll().size());
            assertNotEquals(0, eventRepository.findAll().size());
            assertNotEquals(0, eventRepository.findById(event1.getId()).orElseGet(Event::new).getCouponList().size());
            assertNotEquals(0, couponRepository.findById(coupon2.getId()).orElseGet(Coupon::new).getEventList().size());
        });
        //CleanUp
        try {
            couponRepository.deleteById(coupon1.getId());
            couponRepository.deleteById(coupon2.getId());
            couponRepository.deleteById(coupon3.getId());
            eventRepository.deleteById(event1.getId());
            eventRepository.deleteById(event2.getId());
            eventRepository.deleteById(event3.getId());
        } catch (Exception e) {
            // do nothing
        }
    }
}