package com.bets_be.mapper;

import com.bets_be.domain.User;
import com.bets_be.domain.UserDto;
import com.bets_be.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMapperTestSuit {

    @InjectMocks
    private UserMapper mapper;
    @Mock
    private CouponRepository couponRepository;

    @Test
    void testMapToUser() {
        //Given
        UserDto userDto = new UserDto(1L, "John", "john", "john@gmail", Collections.emptyList());
        when(couponRepository.findAll()).thenReturn(Collections.emptyList());
        //When
        User user = mapper.mapToUser(userDto);
        //Then
        assertAll(
                () -> assertEquals(1L, user.getId()),
                () -> assertEquals("John", user.getName()),
                () -> assertEquals("john", user.getLogin()),
                () -> assertEquals("john@gmail", user.getMail()),
                () -> assertEquals(0, user.getCoupons().size())
        );
    }

    @Test
    void testMapToUserDto() {
        //Given
        User user = new User(1L, "John", "john", "john@gmail", Collections.emptyList());
        //When
        UserDto userDto = mapper.mapToUserDto(user);
        //Then
        assertAll(
                () -> assertEquals(1L, userDto.getId()),
                () -> assertEquals("John", userDto.getName()),
                () -> assertEquals("john", userDto.getLogin()),
                () -> assertEquals("john@gmail", userDto.getMail()),
                () -> assertEquals(0, userDto.getCouponsId().size())
        );
    }

    @Test
    void testMapToUserDtoList() {
        //Given
        User user1 = new User(1L, "John1", "john1", "john1@gmail", Collections.emptyList());
        User user2 = new User(2L, "John2", "john2", "john2@gmail", Collections.emptyList());
        List<User> userList = List.of(user1, user2);
        //When
        List<UserDto> userDtoList = mapper.mapToUserDtoList(userList);
        //Then
        assertAll(
                () -> assertEquals(2, userDtoList.size()),
                () -> assertEquals(2L, userDtoList.get(1).getId()),
                () -> assertEquals("John1", userDtoList.get(0).getName()),
                () -> assertEquals("john2", userDtoList.get(1).getLogin()),
                () -> assertEquals("john1@gmail", userDtoList.get(0).getMail())
        );
    }
}