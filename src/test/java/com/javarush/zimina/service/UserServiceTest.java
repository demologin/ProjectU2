package com.javarush.zimina.service;

import com.javarush.zimina.entity.User;
import com.javarush.zimina.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(new UserRepository());
    }

    @Test
    void registerNewLoginReturnsTrue() {
        assertTrue(userService.register("maria", "secret"));
    }

    @Test
    void registerTakenLoginReturnsFalse() {
        userService.register("maria", "secret");

        assertFalse(userService.register("maria", "other"));
    }

    @Test
    void registeredUserCanLogin() {
        userService.register("maria", "secret");

        User user = userService.login("maria", "secret");

        assertNotNull(user);
        assertEquals("maria", user.getLogin());
    }

    @Test
    void loginWithWrongPasswordReturnsNull() {
        userService.register("maria", "secret");

        assertNull(userService.login("maria", "wrong"));
    }

    @Test
    void loginUnknownUserReturnsNull() {
        assertNull(userService.login("nobody", "secret"));
    }

    @Test
    void registeredUserGetsId() {
        userService.register("maria", "secret");

        User user = userService.login("maria", "secret");

        assertNotNull(user.getId());
    }
}