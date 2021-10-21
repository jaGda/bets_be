package com.bets_be.mapper;

import com.bets_be.builder.CouponBuilder;
import com.bets_be.domain.Coupon;
import com.bets_be.domain.CouponDto;
import com.bets_be.domain.Event;
import com.bets_be.domain.RatesDto;
import com.bets_be.repository.EventDao;
import com.bets_be.repository.UserDao;
import com.bets_be.service.CurrencyConverterService;
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
    private final CurrencyConverterService currencyConverterService;

    public Coupon mapToCoupon(CouponDto couponDto) {
        return new CouponBuilder.Coupon()
                .id(couponDto.getId())
                .user(userDao.findById(couponDto.getUserId()).orElseThrow(
                        () -> new RuntimeException("User of id '" + couponDto.getUserId() + "' doesn't exist")
                ))
                .eventList(couponDto.getEventsId().stream()
                        .map(eventDao::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList()))
                .betCurrency(couponDto.getBetCurrency())
                .stake(couponDto.getStake())
                .winningsPLN(winningInCurrency(couponDto.getEventsId(), couponDto.getStake(), couponDto.getBetCurrency(), "PLN"))
                .winningsUSA(winningInCurrency(couponDto.getEventsId(), couponDto.getStake(), couponDto.getBetCurrency(), "USD"))
                .winningsEUR(winningInCurrency(couponDto.getEventsId(), couponDto.getStake(), couponDto.getBetCurrency(), "EUR"))
                .betDate(LocalDate.parse(couponDto.getBetDate()))
                .betTime(LocalTime.parse(couponDto.getBetTime()))
                .isVictory(couponDto.isVictory() || isWinning(couponDto.getEventsId()))
                .build().getCoupon();
    }

    public CouponDto mapToCouponDto(Coupon coupon) {
        return new CouponDto(
                coupon.getId(),
                coupon.getUser().getId(),
                coupon.getEventList().stream()
                        .map(Event::getId)
                        .collect(Collectors.toList()),
                coupon.getBetCurrency(),
                coupon.getStake(),
                coupon.getWinningsPLN(),
                coupon.getWinningsUSA(),
                coupon.getWinningsEUR(),
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

    private double winningInCurrency(List<Long> events, double stake, String betCurrency, String to) {
        double winning = calculateWinning(events, stake);
        RatesDto ratesDto = currencyConverterService.fetchCurrency(betCurrency, to, winning).getRates();
        return Double.parseDouble(to.equals("PLN") ? ratesDto.getCurrencyPlnName().getRateForAmount()
                : to.equals("EUR") ? ratesDto.getCurrencyEurName().getRateForAmount()
                : ratesDto.getCurrencyUsaName().getRateForAmount());
    }

    private double calculateWinning(List<Long> events, double stake) {
        return stake * events.stream()
                .map(eventDao::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .mapToDouble(Event::getOdd)
                .sum();
    }

    private boolean isWinning(List<Long> events) {
        return events.stream()
                .map(eventDao::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(Event::isWin).count() == events.size();
    }
}
