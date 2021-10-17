package com.bets_be.service;

import com.bets_be.domain.Coupon;
import com.bets_be.repository.CouponDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponDao couponDao;

    public List<Coupon> getAllCoupons() {
        return couponDao.findAll();
    }

    public Coupon getCouponById(Long couponId) {
        return couponDao.findById(couponId).orElseThrow(
                () -> new RuntimeException("Coupon of id '" + couponId + "' doesn't exist")
        );
    }

    public Coupon addCoupon(Coupon coupon) {
        return couponDao.save(coupon);
    }

    public Coupon updateCoupon(Coupon coupon) {
        if (couponDao.findById(coupon.getId()).isEmpty()) {
            throw new RuntimeException("Coupon of id '" + coupon.getId() + "' doesn't exist");
        } else {
            return couponDao.save(coupon);
        }
    }

    public void removeCouponById(Long couponId) {
        couponDao.deleteById(couponId);
    }
}
