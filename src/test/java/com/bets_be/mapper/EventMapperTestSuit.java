package com.bets_be.mapper;

import com.bets_be.domain.Coupon;
import com.bets_be.domain.Event;
import com.bets_be.domain.EventDto;
import com.bets_be.domain.User;
import com.bets_be.repository.CouponRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventMapperTestSuit {

    @InjectMocks
    private EventMapper mapper;

    @Mock
    private CouponRepository couponRepository;

    @Test
    void testMapToEvent() {
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

        List<Coupon> coupons;
        EventDto eventDto = new EventDto(21L, List.of(1L, 2L), 34216L,
                "2021-10-22", "22:10:12", "Half time",
                "HT", 1234L, "Barca", 21242L,
                "Real", 2, 2, "Draw", 3.7, true
        );
        when(couponRepository.findById(1L)).thenReturn(Optional.of(coupon1));
        when(couponRepository.findById(2L)).thenReturn(Optional.of(coupon2));
        //When
        Event event = mapper.mapToEvent(eventDto);
        //Then
        assertAll(
                () -> assertEquals(21L, event.getId()),
                () -> assertEquals(2, event.getCouponList().size()),
                () -> assertEquals(34216L, event.getFixtureId()),
                () -> assertEquals("Barca", event.getHomeTeamName()),
                () -> assertEquals(21242L, event.getAwayTeamId()),
                () -> assertEquals(2, event.getAwayGoals()),
                () -> assertEquals("Draw", event.getBetValue()),
                () -> assertTrue(event.isWin())
        );
    }

    @Test
    void testMapToEventDto() {
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
        Event event = new Event(21L, List.of(coupon1, coupon2), 34216L,
                LocalDate.parse("2021-10-22"), LocalTime.parse("22:10:12"), "Half time",
                "HT", 1234L, "Barca", 21242L,
                "Real", 2, 2, "Draw", 3.7, true
        );
        //When
        EventDto eventDto = mapper.mapToEventDto(event);
        //Then
        assertAll(
                () -> assertEquals(21L, eventDto.getId()),
                () -> assertEquals(2, eventDto.getCouponsId().size()),
                () -> assertEquals(34216L, eventDto.getFixtureId()),
                () -> assertEquals("Barca", eventDto.getHomeTeamName()),
                () -> assertEquals(21242L, eventDto.getAwayTeamId()),
                () -> assertEquals(2, eventDto.getAwayGoals()),
                () -> assertEquals("Draw", eventDto.getBetValue()),
                () -> assertTrue(eventDto.isWin())
        );
    }

    @Test
    void testMapToEventDtoList() {
        //Given
        Event event1 = new Event(1L, Collections.emptyList(), 34216L,
                LocalDate.parse("2021-10-22"), LocalTime.parse("22:10:12"), "Half time",
                "HT", 1234L, "Barca", 21242L,
                "Real", 2, 2, "Draw", 3.7, true);
        Event event2 = new Event(21L, Collections.emptyList(), 34216L,
                LocalDate.parse("2021-10-22"), LocalTime.parse("22:10:12"), "Half time",
                "HT", 1234L, "Barca", 21242L,
                "Real", 2, 2, "Draw", 3.7, true);
        List<Event> events = List.of(event1, event2);
        //When
        List<EventDto> eventDtoList = mapper.mapToEventDtoList(events);
        //Then
        assertEquals(2, eventDtoList.size());
        assertEquals("2021-10-22", eventDtoList.get(0).getFixtureDate());
        assertEquals(21242L, eventDtoList.get(0).getAwayTeamId());
    }
}