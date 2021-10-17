package com.bets_be.controller;

import com.bets_be.domain.CouponDto;
import com.bets_be.mapper.CouponMapper;
import com.bets_be.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService service;
    private final CouponMapper mapper;

    @GetMapping
    public List<CouponDto> getCoupons() {
        return mapper.mapToCouponDtoList(service.getAllCoupons());
    }

    @GetMapping("/{couponId}")
    public CouponDto getCoupon(@PathVariable Long couponId) {
        return mapper.mapToCouponDto(service.getCouponById(couponId));
    }

    @PostMapping
    public CouponDto createCoupon(@RequestBody CouponDto couponDto) {
        return mapper.mapToCouponDto(service.addCoupon(mapper.mapToCoupon(couponDto)));
    }

    @PutMapping
    public CouponDto updateCoupon(@RequestBody CouponDto couponDto) {
        return mapper.mapToCouponDto(service.updateCoupon(mapper.mapToCoupon(couponDto)));
    }

    @DeleteMapping("/{couponId}")
    public void deleteCoupon(@PathVariable Long couponId) {
        service.removeCouponById(couponId);
    }
}
