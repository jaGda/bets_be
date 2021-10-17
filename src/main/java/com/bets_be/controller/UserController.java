package com.bets_be.controller;

import com.bets_be.domain.UserDto;
import com.bets_be.mapper.UserMapper;
import com.bets_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping
    public List<UserDto> getUsers() {
        return mapper.mapToUserDtoList(service.getAllUsers());
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        return mapper.mapToUserDto(service.getUserById(userId));
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        return mapper.mapToUserDto(service.addUser(mapper.mapToUser(userDto)));
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return mapper.mapToUserDto(service.updateUser(mapper.mapToUser(userDto)));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        service.removeUserById(userId);
    }
}
