package com.bets_be.mapper;

import com.bets_be.domain.*;
import com.bets_be.repository.EventRepository;
import com.bets_be.repository.UserRepository;
import com.bets_be.service.CurrencyConverterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouponMapperTestSuit {

    @InjectMocks
    private CouponMapper mapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private CurrencyConverterService currencyConverterService;

    @Test
    void testMapToCoupon() {
        //Given
        User user = new User(28L, "name", "login", "mail", Collections.emptyList());
        CouponDto dto = new CouponDto(
                0L, 28L, List.of(1L, 2L, 3L),
                "PLN", 25.0, 0,
                0, 0, "2021-01-01",
                "15:14:13", false
        );

        when(userRepository.findById(28L)).thenReturn(Optional.of(user));
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(new Event()));
        when(currencyConverterService.fetchCurrency("PLN", "PLN", 0)).thenReturn(new CurrencyDto(
                "", "", "", "", new RatesDto(
                new CurrencyNameDto("", "", "123.55"),
                null,
                null
        )));
        when(currencyConverterService.fetchCurrency("PLN", "USD", 0)).thenReturn(new CurrencyDto(
                "", "", "", "", new RatesDto(
                null,
                new CurrencyNameDto("", "", "123.55"),
                null
        )));
        when(currencyConverterService.fetchCurrency("PLN", "EUR", 0)).thenReturn(new CurrencyDto(
                "", "", "", "", new RatesDto(
                null,
                null,
                new CurrencyNameDto("", "", "123.55")
        )));
        //When
        Coupon coupon = mapper.mapToCoupon(dto);
        //Then
        Assertions.assertAll(
                () -> Assertions.assertEquals(0L, coupon.getId()),
                () -> Assertions.assertEquals(3, coupon.getEventList().size()),
                () -> Assertions.assertEquals(123.55, coupon.getWinningsPLN()),
                () -> Assertions.assertEquals(123.55, coupon.getWinningsUSA()),
                () -> Assertions.assertEquals(123.55, coupon.getWinningsEUR()),
                () -> Assertions.assertEquals(LocalDate.parse("2021-01-01"), coupon.getBetDate()),
                () -> Assertions.assertEquals(LocalTime.parse("15:14:13"), coupon.getBetTime()),
                () -> Assertions.assertFalse(coupon.isVictory())
        );
    }

    @Test
    void testMapToCouponDto() {
        //Given
        User user = new User(28L, "name", "login", "mail", Collections.emptyList());
        Coupon coupon = new Coupon(1L, user, Collections.emptyList(), "EUR",
                25, 134, 123, 145,
                LocalDate.of(2021, 10, 22), LocalTime.of(20, 36, 22), false);
        //When
        CouponDto couponDto = mapper.mapToCouponDto(coupon);
        //Then
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, couponDto.getId()),
                () -> Assertions.assertEquals(28L, couponDto.getUserId()),
                () -> Assertions.assertEquals(0, couponDto.getEventsId().size()),
                () -> Assertions.assertEquals("EUR", couponDto.getBetCurrency()),
                () -> Assertions.assertEquals(134, couponDto.getWinningsPLN()),
                () -> Assertions.assertEquals(123, couponDto.getWinningsUSA()),
                () -> Assertions.assertEquals(145, couponDto.getWinningsEUR()),
                () -> Assertions.assertEquals("2021-10-22", couponDto.getBetDate()),
                () -> Assertions.assertEquals("20:36:22", couponDto.getBetTime()),
                () -> Assertions.assertFalse(coupon.isVictory())
        );
    }

    @Test
    void testMapToCouponDtoList() {
        //Given
        User user = new User(28L, "name", "login", "mail", Collections.emptyList());
        Coupon coupon1 = new Coupon(1L, user, Collections.emptyList(), "EUR",
                25, 134, 123, 145,
                LocalDate.of(2021, 10, 22),
                LocalTime.of(20, 36, 22), false);
        Coupon coupon2 = new Coupon(2L, user, Collections.emptyList(), "PLN",
                25, 134, 123, 145,
                LocalDate.of(2021, 10, 22),
                LocalTime.of(20, 36, 22), true);
        //When
        List<CouponDto> couponDtoList = mapper.mapToCouponDtoList(List.of(coupon1, coupon2));
        //Then
        Assertions.assertAll(
                () -> Assertions.assertEquals(2L, couponDtoList.get(1).getId()),
                () -> Assertions.assertEquals(28L, couponDtoList.get(0).getUserId()),
                () -> Assertions.assertEquals(2, couponDtoList.size()),
                () -> Assertions.assertEquals("PLN", couponDtoList.get(1).getBetCurrency()),
                () -> Assertions.assertEquals("2021-10-22", couponDtoList.get(0).getBetDate()),
                () -> Assertions.assertEquals("20:36:22", couponDtoList.get(1).getBetTime()),
                () -> Assertions.assertFalse(couponDtoList.get(0).isVictory())
        );
    }
}