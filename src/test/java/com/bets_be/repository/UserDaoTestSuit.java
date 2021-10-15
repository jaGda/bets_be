package com.bets_be.repository;

import com.bets_be.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserDaoTestSuit {

    @Autowired
    private UserDao userDao;

    @Test
    void testUserDaoSave() {
        //Given
        User user = new User("name", "login", "@wp.pl");
        //When
        userDao.save(user);
        //Then
        Long id = user.getId();
        Optional<User> savedUser = userDao.findById(id);
        assertTrue(savedUser.isPresent());
        //CleanUp
        userDao.deleteById(id);
    }

    @Test
    void testUserDaoFindByMail() {
        //Given
        User user = new User("name", "login", "@wp.pl");
        userDao.save(user);
        String mail = user.getMail();
        //When
        List<User> savedUsers = userDao.findByMail(mail);
        //Then
        assertEquals(1, savedUsers.size());
        //CleanUp
        userDao.deleteById(user.getId());
    }
}