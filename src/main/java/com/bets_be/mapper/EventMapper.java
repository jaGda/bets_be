package com.bets_be.mapper;

import com.bets_be.domain.Coupon;
import com.bets_be.domain.Event;
import com.bets_be.domain.EventDto;
import com.bets_be.repository.CouponDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventMapper {

    private final CouponDao couponDao;

    public Event mapToEvent(EventDto eventDto) {
        return new Event(
                eventDto.getId(),
                eventDto.getCouponsId().stream()
                        .map(couponDao::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList())
        );
    }

    public EventDto mapToEventDto(Event event) {
        return new EventDto(
                event.getId(),
                event.getCouponList().stream()
                        .map(Coupon::getId)
                        .collect(Collectors.toList())
        );
    }

    public List<EventDto> mapToEventDtoList(List<Event> events) {
        return events.stream()
                .map(this::mapToEventDto)
                .collect(Collectors.toList());
    }
}
