package com.bets_be.repository;

import com.bets_be.domain.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Transactional
@Repository
public interface EventDao extends CrudRepository<Event, Long> {

    @Override
    List<Event> findAll();

    List<Event> findAllByFixtureId(Long id);

    List<Event> findAllByFixtureDate(LocalDate date);

    List<Event> findAllByFixtureDateBetween(LocalDate from, LocalDate to);
}
