package com.bets_be.service;

import com.bets_be.domain.User;
import com.bets_be.loggerInfo.LoggerMessage;
import com.bets_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        LOGGER.info(LoggerMessage.FETCH.getMessage("getAllUsers"));
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        LOGGER.info(LoggerMessage.FETCH.getMessage("getUserById"));
        return userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User of id '" + userId + "' doesn't exist")
        );
    }

    public User addUser(User user) {
        LOGGER.info(LoggerMessage.CREATE.getMessage("addUser"));
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        LOGGER.info(LoggerMessage.UPDATE.getMessage("updateUser"));
        if (userRepository.findById(user.getId()).isEmpty()) {
            throw new RuntimeException("User of id '" + user.getId() + "' doesn't exist");
        } else {
            return userRepository.save(user);
        }
    }

    public void removeUserById(Long userId) {
        LOGGER.info(LoggerMessage.DELETE.getMessage("removeUserById"));
        userRepository.deleteById(userId);
    }
}
