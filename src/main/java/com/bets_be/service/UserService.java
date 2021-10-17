package com.bets_be.service;

import com.bets_be.domain.User;
import com.bets_be.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public User getUserById(Long userId) {
        return userDao.findById(userId).orElseThrow(
                () -> new RuntimeException("User of id '" + userId + "' doesn't exist")
        );
    }

    public User addUser(User user) {
        return userDao.save(user);
    }

    public User updateUser(User user) {
        if (userDao.findById(user.getId()).isEmpty()) {
            throw new RuntimeException("User of id '" + user.getId() + "' doesn't exist");
        } else {
            return userDao.save(user);
        }
    }

    public void removeUserById(Long userId) {
        userDao.deleteById(userId);
    }
}
