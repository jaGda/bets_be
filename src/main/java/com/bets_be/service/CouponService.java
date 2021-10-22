package com.bets_be.service;

import com.bets_be.domain.Coupon;
import com.bets_be.loggerInfo.LoggerMessage;
import com.bets_be.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouponService.class);
    private final CouponRepository couponRepository;

    public List<Coupon> getAllCoupons() {
        LOGGER.info(LoggerMessage.FETCH.getMessage("getAllCoupons"));
        return couponRepository.findAll();
    }

    public Coupon getCouponById(Long couponId) {
        LOGGER.info(LoggerMessage.FETCH.getMessage("getCouponById"));
        return couponRepository.findById(couponId).orElseThrow(
                () -> new RuntimeException("Coupon of id '" + couponId + "' doesn't exist")
        );
    }

    public Coupon addCoupon(Coupon coupon) {
        LOGGER.info(LoggerMessage.CREATE.getMessage("addCoupon"));
        return couponRepository.save(coupon);
    }

    public Coupon updateCoupon(Coupon coupon) {
        LOGGER.info(LoggerMessage.UPDATE.getMessage("updateCoupon"));
        if (couponRepository.findById(coupon.getId()).isEmpty()) {
            throw new RuntimeException("Coupon of id '" + coupon.getId() + "' doesn't exist");
        } else {
            return couponRepository.save(coupon);
        }
    }

    public void removeCouponById(Long couponId) {
        LOGGER.info(LoggerMessage.DELETE.getMessage("removeCouponById"));
        couponRepository.deleteById(couponId);
    }
}
