package com.bets_be.controller;

import com.bets_be.domain.UserDto;
import com.bets_be.mapper.UserMapper;
import com.bets_be.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public List<UserDto> getUsers() {
        return userMapper.mapToUserDtoList(userDao.findAll());
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        return userMapper.mapToUserDto(userDao.findById(userId).orElseThrow(
                () -> new RuntimeException("User of id '" + userId + "' doesn't exist")
        ));
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userMapper.mapToUserDto(userDao.save(userMapper.mapToUser(userDto)));
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserDto userDto) {
        if (userDao.findById(userDto.getId()).isEmpty()) {
            throw new RuntimeException("User of id '" + userDto.getId() + "' doesn't exist");
        } else {
            return userMapper.mapToUserDto(userDao.save(userMapper.mapToUser(userDto)));
        }
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userDao.deleteById(userId);
    }
}
