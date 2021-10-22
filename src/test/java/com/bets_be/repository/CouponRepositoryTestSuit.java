package com.bets_be.repository;

import com.bets_be.domain.Coupon;
import com.bets_be.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CouponRepositoryTestSuit {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCouponDaoSaveWithCoupons() {
        //Given
        User user = new User("name1", "login1", "1@wp.pl");
        Coupon coupon1 = new Coupon(user);
        Coupon coupon2 = new Coupon(user);
        //When
        userRepository.save(user);
        couponRepository.saveAll(List.of(coupon1, coupon2));
        //Then
        List<Coupon> result = couponRepository.findAllByUser(user);
        assertEquals(2, result.size());
        //CleanUp
        try {
            couponRepository.deleteById(coupon1.getId());
            couponRepository.deleteById(coupon2.getId());
            userRepository.deleteById(user.getId());
        } catch (Exception e) {
            // do nothing
        }
    }
}