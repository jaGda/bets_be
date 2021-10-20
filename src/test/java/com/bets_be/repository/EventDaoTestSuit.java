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
        Event event1 = new Event(0L);
        event1.addCoupon(coupon1);
        event1.addCoupon(coupon2);
        event1.addCoupon(coupon3);
        Event event2 = new Event(1L);
        event2.addCoupon(coupon2);
        event2.addCoupon(coupon3);
        Event event3 = new Event(2L);
        event3.addCoupon(coupon2);
        //When
        couponDao.saveAll(List.of(coupon1, coupon2, coupon3));
//        eventDao.saveAll(List.of(event1, event2, event3));
        //Then
        assertAll(() -> {
            assertNotEquals(0, couponDao.findAll().size());
            assertNotEquals(0, eventDao.findAll().size());
            assertNotEquals(0, eventDao.findById(event1.getId()).orElseGet(Event::new).getCouponList().size());
            assertNotEquals(0, couponDao.findById(coupon2.getId()).orElseGet(Coupon::new).getEventList().size());
        });
        //CleanUp
        try {
            couponDao.deleteById(coupon1.getId());
            couponDao.deleteById(coupon2.getId());
            couponDao.deleteById(coupon3.getId());
            eventDao.deleteById(event1.getId());
            eventDao.deleteById(event2.getId());
            eventDao.deleteById(event3.getId());
        } catch (Exception e) {
            // do nothing
        }
    }
}