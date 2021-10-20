package com.bets_be.mapper;

import com.bets_be.domain.Coupon;
import com.bets_be.domain.CouponDto;
import com.bets_be.domain.Event;
import com.bets_be.repository.EventDao;
import com.bets_be.repository.UserDao;
import com.bets_be.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponMapper {

    private final UserDao userDao;
    private final EventDao eventDao;
    private final CouponService couponService;

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
                        .collect(Collectors.toList()),
                couponDto.getStake(),
                couponService.calculateWinning(couponDto.getEventsId(), couponDto.getStake()),
                LocalDate.parse(couponDto.getBetDate()),
                LocalTime.parse(couponDto.getBetTime()),
                couponDto.isVictory() || couponService.isWinning(couponDto.getEventsId())
        );
    }

    public CouponDto mapToCouponDto(Coupon coupon) {
        return new CouponDto(
                coupon.getId(),
                coupon.getUser().getId(),
                coupon.getEventList().stream()
                        .map(Event::getId)
                        .collect(Collectors.toList()),
                coupon.getStake(),
                coupon.getWinnings(),
                coupon.getBetDate().toString(),
                coupon.getBetTime().toString(),
                coupon.isVictory()
        );
    }

    public List<CouponDto> mapToCouponDtoList(List<Coupon> coupons) {
        return coupons.stream()
                .map(this::mapToCouponDto)
                .collect(Collectors.toList());
    }
}
