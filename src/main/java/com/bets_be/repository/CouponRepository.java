package com.bets_be.repository;

import com.bets_be.domain.Coupon;
import com.bets_be.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface CouponRepository extends CrudRepository<Coupon, Long> {

    @Override
    List<Coupon> findAll();

    List<Coupon> findAllByUser(User user);
}
