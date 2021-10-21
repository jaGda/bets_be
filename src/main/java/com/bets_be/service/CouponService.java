package com.bets_be.service;

import com.bets_be.domain.Coupon;
import com.bets_be.loggerInfo.LoggerMessage;
import com.bets_be.repository.CouponDao;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouponService.class);
    private final CouponDao couponDao;

    public List<Coupon> getAllCoupons() {
        LOGGER.info(LoggerMessage.FETCH.getMessage("getAllCoupons"));
        return couponDao.findAll();
    }

    public Coupon getCouponById(Long couponId) {
        LOGGER.info(LoggerMessage.FETCH.getMessage("getCouponById"));
        return couponDao.findById(couponId).orElseThrow(
                () -> new RuntimeException("Coupon of id '" + couponId + "' doesn't exist")
        );
    }

    public Coupon addCoupon(Coupon coupon) {
        LOGGER.info(LoggerMessage.CREATE.getMessage("addCoupon"));
        return couponDao.save(coupon);
    }

    public Coupon updateCoupon(Coupon coupon) {
        LOGGER.info(LoggerMessage.UPDATE.getMessage("updateCoupon"));
        if (couponDao.findById(coupon.getId()).isEmpty()) {
            throw new RuntimeException("Coupon of id '" + coupon.getId() + "' doesn't exist");
        } else {
            return couponDao.save(coupon);
        }
    }

    public void removeCouponById(Long couponId) {
        LOGGER.info(LoggerMessage.DELETE.getMessage("removeCouponById"));
        couponDao.deleteById(couponId);
    }
}
