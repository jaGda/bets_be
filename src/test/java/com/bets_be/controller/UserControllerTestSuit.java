package com.bets_be.controller;

import com.bets_be.domain.User;
import com.bets_be.domain.UserDto;
import com.bets_be.mapper.UserMapper;
import com.bets_be.service.UserService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringJUnitWebConfig
@WebMvcTest(UserController.class)
class UserControllerTestSuit {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserMapper mapper;
    @MockBean
    private UserService service;

    @Test
    void shouldGetUsers() throws Exception {
        //Given
        List<User> users = List.of(
                new User(1L, "John1", "john1", "john1@gmail", Collections.emptyList()),
                new User(2L, "John2", "john2", "john2@gmail", Collections.emptyList())
        );
        List<UserDto> userDtoList = List.of(
                new UserDto(1L, "John1", "john1", "john1@gmail", Collections.emptyList()),
                new UserDto(2L, "John2", "john2", "john2@gmail", Collections.emptyList())
        );

        when(service.getAllUsers()).thenReturn(users);
        when(mapper.mapToUserDtoList(users)).thenReturn(userDtoList);
        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].mail", Matchers.is("john2@gmail")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("John1")));
    }

    @Test
    void shouldGetUser() throws Exception {
        //Given
        UserDto userDto = new UserDto(1L, "John", "john", "john@gmail", Collections.emptyList());
        User user = new User(1L, "John", "john", "john@gmail", Collections.emptyList());

        when(service.getUserById(1L)).thenReturn(user);
        when(mapper.mapToUserDto(user)).thenReturn(userDto);
        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("John")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login", Matchers.is("john")));
    }

    @Test
    void shouldCreateUser() throws Exception {
        //Given
        UserDto userDto = new UserDto(1L, "John", "john", "john@gmail", Collections.emptyList());
        User user = new User(1L, "John", "john", "john@gmail", Collections.emptyList());

        when(mapper.mapToUser(any(UserDto.class))).thenReturn(user);
        when(service.addUser(user)).thenReturn(user);
        when(mapper.mapToUserDto(user)).thenReturn(userDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);
        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("John")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mail", Matchers.is("john@gmail")));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        //Given
        UserDto userDto = new UserDto(1L, "John", "john", "john@gmail", Collections.emptyList());
        User user = new User(1L, "John", "john", "john@gmail", Collections.emptyList());

        when(mapper.mapToUser(any(UserDto.class))).thenReturn(user);
        when(service.updateUser(user)).thenReturn(user);
        when(mapper.mapToUserDto(user)).thenReturn(userDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);
        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("John")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mail", Matchers.is("john@gmail")));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        //Given&When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/users/23")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(service, times(1)).removeUserById(23L);
    }
}