package com.bets_be.controller;

import com.bets_be.domain.CouponDto;
import com.bets_be.mapper.CouponMapper;
import com.bets_be.repository.CouponDao;
import com.bets_be.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private UserDao userDao;

    @GetMapping
    public List<CouponDto> getCoupons() {
        return couponMapper.mapToCouponDtoList(couponDao.findAll());
    }

    @GetMapping("/{couponId}")
    public CouponDto getCoupon(@PathVariable Long couponId) {
        return couponMapper.mapToCouponDto(couponDao.findById(couponId).orElseThrow(
                () -> new RuntimeException("Coupon of id '" + couponId + "' doesn't exist")
        ));
    }

    @GetMapping("/{userId}")
    public List<CouponDto> getAllUserCoupons(@PathVariable Long userId) {
        return couponMapper.mapToCouponDtoList(
                couponDao.findAllByUser(userDao.findById(userId).orElseThrow(
                        () -> new RuntimeException("User of id '" + userId + "' doesn't exist")
                ))
        );
    }

    @PostMapping
    public CouponDto createCoupon(@RequestBody CouponDto couponDto) {
        return couponMapper.mapToCouponDto(couponDao.save(couponMapper.mapToCoupon(couponDto)));
    }

    @PutMapping
    public CouponDto updateCoupon(@RequestBody CouponDto couponDto) {
        if (couponDao.findById(couponDto.getId()).isEmpty()) {
            throw new RuntimeException("Coupon of id '" + couponDto.getId() + "' doesn't exist");
        } else {
            return couponMapper.mapToCouponDto(couponDao.save(couponMapper.mapToCoupon(couponDto)));
        }
    }

    @DeleteMapping("/{couponId}")
    public void deleteCoupon(@PathVariable Long couponId) {
        couponDao.deleteById(couponId);
    }
}
