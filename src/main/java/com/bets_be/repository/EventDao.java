package com.bets_be.repository;

import com.bets_be.domain.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface EventDao extends CrudRepository<Event, Long> {

    @Override
    List<Event> findAll();

    Optional<Event> findByFixtureId(Long id);
}
