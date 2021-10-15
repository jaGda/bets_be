package com.bets_be.mapper;

import com.bets_be.domain.Coupon;
import com.bets_be.domain.User;
import com.bets_be.domain.UserDto;
import com.bets_be.repository.CouponDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    @Autowired
    private CouponDao couponDao;

    public User mapToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getLogin(),
                userDto.getMail(),
                couponDao.findAll().stream()
                        .filter(coupon -> coupon.getUser().getId().equals(userDto.getId()))
                        .collect(Collectors.toList())
        );
    }

    public UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getLogin(),
                user.getMail(),
                user.getCoupons().stream()
                        .map(Coupon::getId)
                        .collect(Collectors.toList())
        );
    }

    public List<UserDto> mapToUserDtoList(List<User> users) {
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
}
