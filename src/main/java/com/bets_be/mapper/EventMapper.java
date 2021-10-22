package com.bets_be.mapper;

import com.bets_be.domain.Coupon;
import com.bets_be.domain.Event;
import com.bets_be.domain.EventDto;
import com.bets_be.domain.FixtureDto;
import com.bets_be.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventMapper {

    private final CouponRepository couponRepository;

    public Event mapToEvent(EventDto eventDto) {
        return new Event(
                eventDto.getId(),
                eventDto.getCouponsId().stream()
                        .map(couponRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList()),
                eventDto.getFixtureId(),
                LocalDate.parse(eventDto.getFixtureDate()),
                LocalTime.parse(eventDto.getFixtureTime()),
                eventDto.getStatusLong(),
                eventDto.getStatusShort(),
                eventDto.getHomeTeamId(),
                eventDto.getHomeTeamName(),
                eventDto.getAwayTeamId(),
                eventDto.getAwayTeamName(),
                eventDto.getHomeGoals(),
                eventDto.getAwayGoals(),
                eventDto.getBetValue(),
                eventDto.getOdd(),
                eventDto.isWin()
        );
    }

    public EventDto mapToEventDto(Event event) {
        return new EventDto(
                event.getId(),
                event.getCouponList().stream()
                        .map(Coupon::getId)
                        .collect(Collectors.toList()),
                event.getFixtureId(),
                event.getFixtureDate().toString(),
                event.getFixtureTime().toString(),
                event.getStatusLong(),
                event.getStatusShort(),
                event.getHomeTeamId(),
                event.getHomeTeamName(),
                event.getAwayTeamId(),
                event.getAwayTeamName(),
                event.getHomeGoals(),
                event.getAwayGoals(),
                event.getBetValue(),
                event.getOdd(),
                event.isWin()
        );
    }

    public List<EventDto> mapToEventDtoList(List<Event> events) {
        return events.stream()
                .map(this::mapToEventDto)
                .collect(Collectors.toList());
    }

    public Event mapToEvent(FixtureDto fixtureDto, String betValue) {
        return new Event(
                0L,
                new ArrayList<>(),
                fixtureDto.getFixtureDetailsDto().getId(),
                LocalDate.parse(OffsetDateTime.parse(fixtureDto.getFixtureDetailsDto().getDate()).format(DateTimeFormatter.ISO_LOCAL_DATE)),
                LocalTime.parse(OffsetDateTime.parse(fixtureDto.getFixtureDetailsDto().getDate()).format(DateTimeFormatter.ISO_LOCAL_TIME)),
                fixtureDto.getFixtureDetailsDto().getStatusDto().getLongName(),
                fixtureDto.getFixtureDetailsDto().getStatusDto().getShortName(),
                fixtureDto.getTeamsDto().getHomeDto().getId(),
                fixtureDto.getTeamsDto().getHomeDto().getName(),
                fixtureDto.getTeamsDto().getAwayDto().getId(),
                fixtureDto.getTeamsDto().getAwayDto().getName(),
                fixtureDto.getGoalsDto().getHome(),
                fixtureDto.getGoalsDto().getAway(),
                betValue, 0,
                false
        );
    }

    public Event mapToEvent(Event event, FixtureDto fixtureDto) {
        event.setFixtureDate(LocalDate.parse(OffsetDateTime.parse(fixtureDto.getFixtureDetailsDto().getDate()).format(DateTimeFormatter.ISO_LOCAL_DATE)));
        event.setFixtureTime(LocalTime.parse(OffsetDateTime.parse(fixtureDto.getFixtureDetailsDto().getDate()).format(DateTimeFormatter.ISO_LOCAL_TIME)));
        event.setStatusLong(fixtureDto.getFixtureDetailsDto().getStatusDto().getLongName());
        event.setStatusShort(fixtureDto.getFixtureDetailsDto().getStatusDto().getShortName());
        event.setHomeTeamId(fixtureDto.getTeamsDto().getHomeDto().getId());
        event.setHomeTeamName(fixtureDto.getTeamsDto().getHomeDto().getName());
        event.setAwayTeamId(fixtureDto.getTeamsDto().getAwayDto().getId());
        event.setAwayTeamName(fixtureDto.getTeamsDto().getAwayDto().getName());
        event.setHomeGoals(fixtureDto.getGoalsDto().getHome());
        event.setAwayGoals(fixtureDto.getGoalsDto().getAway());
        event.setWin(checkIsWin(event.getStatusShort(), event.getBetValue(), fixtureDto.getGoalsDto().getHome(), fixtureDto.getGoalsDto().getAway()));
        return event;
    }

    public boolean checkIsWin(String status, String betValue, int homeGoals, int awayGoals) {
        return status.equals("FT") && (homeGoals > awayGoals ? "Home".equals(betValue)
                : homeGoals == awayGoals ? "Draw".equals(betValue)
                : "Away".equals(betValue));
    }
}
