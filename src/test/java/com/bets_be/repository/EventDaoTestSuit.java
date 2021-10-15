package com.bets_be.repository;

import com.bets_be.domain.Coupon;
import com.bets_be.domain.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EventDaoTestSuit {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private CouponDao couponDao;

    @Test
    void testSaveManyToMany() {
        //Given
        Coupon coupon1 = new Coupon();
        Coupon coupon2 = new Coupon();
        Coupon coupon3 = new Coupon();
        Event event1 = new Event();
        event1.addCoupon(coupon1);
        event1.addCoupon(coupon2);
        event1.addCoupon(coupon3);
        Event event2 = new Event();
        event2.addCoupon(coupon2);
        event2.addCoupon(coupon3);
        Event event3 = new Event();
        event3.addCoupon(coupon2);
        //When
        eventDao.saveAll(List.of(event1, event2, event3));
        //Then
        assertAll(() -> {
            assertEquals(3, couponDao.findAll().size());
            assertEquals(3, eventDao.findAll().size());
            assertEquals(3, eventDao.findById(event1.getId()).orElseGet(Event::new).getCouponList().size());
            assertEquals(3, couponDao.findById(coupon2.getId()).orElseGet(Coupon::new).getEventList().size());
        });

        //CleanUp
        try {
            eventDao.deleteById(event1.getId());
            eventDao.deleteById(event2.getId());
            eventDao.deleteById(event3.getId());
        } catch (Exception e) {
            // do nothing
        }
    }
}