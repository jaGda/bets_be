package com.bets_be.repository;

import com.bets_be.domain.Coupon;
import com.bets_be.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CouponDaoTestSuit {

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private UserDao userDao;

    @Test
    void testCouponDaoSaveWithCoupons() {
        //Given
        User user = new User("name1", "login1", "1@wp.pl");
        Coupon coupon1 = new Coupon(user);
        Coupon coupon2 = new Coupon(user);
        //When
        couponDao.saveAll(List.of(coupon1, coupon2));
        //Then
        List<Coupon> result = couponDao.findAllByUser(user);
        assertEquals(2, result.size());
        //CleanUp
        try {
            couponDao.deleteById(coupon1.getId());
        } catch (Exception e) {
            // do nothing
        }
    }
}