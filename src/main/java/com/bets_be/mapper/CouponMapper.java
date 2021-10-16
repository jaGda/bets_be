package com.bets_be.mapper;

import com.bets_be.domain.Coupon;
import com.bets_be.domain.CouponDto;
import com.bets_be.domain.Event;
import com.bets_be.repository.EventDao;
import com.bets_be.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CouponMapper {

    @Autowired
    private UserDao userDao;

    @Autowired
    private EventDao eventDao;

    public Coupon mapToCoupon(CouponDto couponDto) {
        return new Coupon(
                couponDto.getId(),
                userDao.findById(couponDto.getUserId()).orElseThrow(
                        () -> new RuntimeException("User of id '" + couponDto.getUserId() + "' doesn't exist")
                ),
                couponDto.getEventsId().stream()
                        .map(eventDao::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList())
        );
    }

    public CouponDto mapToCouponDto(Coupon coupon) {
        return new CouponDto(
                coupon.getId(),
                coupon.getUser().getId(),
                coupon.getEventList().stream()
                        .map(Event::getId)
                        .collect(Collectors.toList())
        );
    }

    public List<CouponDto> mapToCouponDtoList(List<Coupon> coupons) {
        return coupons.stream()
                .map(this::mapToCouponDto)
                .collect(Collectors.toList());
    }
}
