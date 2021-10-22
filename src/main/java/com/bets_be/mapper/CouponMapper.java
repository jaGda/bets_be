package com.bets_be.mapper;

import com.bets_be.builder.CouponBuilder;
import com.bets_be.domain.Coupon;
import com.bets_be.domain.CouponDto;
import com.bets_be.domain.Event;
import com.bets_be.domain.RatesDto;
import com.bets_be.repository.EventRepository;
import com.bets_be.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CurrencyConverterService currencyConverterService;

    public Coupon mapToCoupon(CouponDto couponDto) {
        List<Event> events = couponDto.getEventsId().stream()
                .map(eventRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return new CouponBuilder.Coupon()
                .id(couponDto.getId())
                .user(userRepository.findById(couponDto.getUserId()).orElseThrow(
                        () -> new RuntimeException("User of id '" + couponDto.getUserId() + "' doesn't exist")
                ))
                .eventList(events)
                .betCurrency(couponDto.getBetCurrency())
                .stake(couponDto.getStake())
                .winningsPLN(winningInCurrency(events, couponDto.getStake(), couponDto.getBetCurrency(), "PLN"))
                .winningsUSA(winningInCurrency(events, couponDto.getStake(), couponDto.getBetCurrency(), "USD"))
                .winningsEUR(winningInCurrency(events, couponDto.getStake(), couponDto.getBetCurrency(), "EUR"))
                .betDate(LocalDate.parse(couponDto.getBetDate()))
                .betTime(LocalTime.parse(couponDto.getBetTime()))
                .isVictory(couponDto.isVictory() || isWinning(events))
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

    private double winningInCurrency(List<Event> events, double stake, String betCurrency, String to) {
        double winning = calculateWinning(events, stake);
        RatesDto ratesDto = currencyConverterService.fetchCurrency(betCurrency, to, winning).getRates();
        return Double.parseDouble(to.equals("PLN") ? ratesDto.getCurrencyPlnName().getRateForAmount()
                : to.equals("EUR") ? ratesDto.getCurrencyEurName().getRateForAmount()
                : ratesDto.getCurrencyUsaName().getRateForAmount());
    }

    private double calculateWinning(List<Event> events, double stake) {
        return stake * events.stream()
                .mapToDouble(Event::getOdd)
                .sum();
    }

    private boolean isWinning(List<Event> events) {
        return events.stream()
                .filter(Event::isWin)
                .count() == events.size();
    }
}
