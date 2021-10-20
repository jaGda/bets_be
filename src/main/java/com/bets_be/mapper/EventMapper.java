package com.bets_be.mapper;

import com.bets_be.domain.*;
import com.bets_be.repository.CouponDao;
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

    private final CouponDao couponDao;

    public Event mapToEvent(EventDto eventDto) {
        return new Event(
                eventDto.getId(),
                eventDto.getCouponsId().stream()
                        .map(couponDao::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList()),
                eventDto.getFixtureId(),
                eventDto.getFixtureDate(),
                eventDto.getFixtureTime(),
                eventDto.getStatusLong(),
                eventDto.getStatusShort(),
                eventDto.getHomeTeamId(),
                eventDto.getHomeTeamName(),
                eventDto.getAwayTeamId(),
                eventDto.getAwayTeamName(),
                eventDto.getHomeGoals(),
                eventDto.getAwayGoals(),
                eventDto.getBetOnHome(),
                eventDto.getBetOnDraw(),
                eventDto.getBetOnAway()
        );
    }

    public EventDto mapToEventDto(Event event) {
        return new EventDto(
                event.getId(),
                event.getCouponList().stream()
                        .map(Coupon::getId)
                        .collect(Collectors.toList()),
                event.getFixtureId(),
                event.getFixtureDate(),
                event.getFixtureTime(),
                event.getStatusLong(),
                event.getStatusShort(),
                event.getHomeTeamId(),
                event.getHomeTeamName(),
                event.getAwayTeamId(),
                event.getAwayTeamName(),
                event.getHomeGoals(),
                event.getAwayGoals(),
                event.getBetOnHome(),
                event.getBetOnDraw(),
                event.getBetOnAway()
        );
    }

    public List<EventDto> mapToEventDtoList(List<Event> events) {
        return events.stream()
                .map(this::mapToEventDto)
                .collect(Collectors.toList());
    }

    public Event mapToEvent(FixtureDto fixtureDto) {
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
                0, 0, 0
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
        return event;
    }

    public Event mapToEvent(Event event, OddDto oddDto) {
        event.setBetOnHome(Double.parseDouble(oddDto.getBookmakerDtoList().get(0).getBetsDtoList().get(0).getBetValueDtoList().get(0).getOdd()));
        event.setBetOnDraw(Double.parseDouble(oddDto.getBookmakerDtoList().get(0).getBetsDtoList().get(0).getBetValueDtoList().get(1).getOdd()));
        event.setBetOnAway(Double.parseDouble(oddDto.getBookmakerDtoList().get(0).getBetsDtoList().get(0).getBetValueDtoList().get(2).getOdd()));
        return event;
    }
}
