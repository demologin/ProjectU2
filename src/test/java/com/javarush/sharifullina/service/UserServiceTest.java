package com.javarush.sharifullina.service;

import com.javarush.sharifullina.entity.User;
import com.javarush.sharifullina.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getAllShouldReturnAllUsers() {
        List<User> expectedUsers = List.of(new User(), new User());
        when(userRepository.getAll()).thenReturn(expectedUsers);

        Collection<User> actualUsers = userService.getAll();

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void getSortedLimitShouldSortDescendingByScoreAndApplyLimit() {
        User userLow = User.builder().login("low").score(10).build();
        User userHigh = User.builder().login("high").score(100).build();
        User userMid = User.builder().login("mid").score(50).build();

        when(userRepository.getAll()).thenReturn(List.of(userLow, userHigh, userMid));

        Collection<User> result = userService.getSortedLimit(2);
        List<User> resultList = List.copyOf(result);

        assertEquals(2, resultList.size());
        assertEquals(userHigh, resultList.get(0));
        assertEquals(userMid, resultList.get(1));
    }

    @Test
    void getUserByIdShouldReturnUserWhenExists() {
        User expectedUser = User.builder().id(1L).build();
        when(userRepository.get(1L)).thenReturn(Optional.of(expectedUser));

        User actualUser = userService.getUser(1L);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void getUserByIdShouldReturnNullWhenNotExists() {
        when(userRepository.get(99L)).thenReturn(Optional.empty());

        assertNull(userService.getUser(99L));
    }

    @Test
    void getUserByLoginAndPasswordShouldReturnUserWhenMatched() {
        User expectedUser = User.builder().login("admin").password("1234").build();
        when(userRepository.find(any(User.class))).thenReturn(Stream.of(expectedUser));

        User actualUser = userService.getUser("admin", "1234");

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void getUserByLoginAndPasswordShouldReturnNullWhenNotMatched() {
        when(userRepository.find(any(User.class))).thenReturn(Stream.empty());

        assertNull(userService.getUser("wrong", "pass"));
    }

    @Test
    void isRepeatLoginShouldReturnTrueWhenLoginExists() {
        User existingUser = User.builder().login("ivan").build();
        when(userRepository.find(any(User.class))).thenReturn(Stream.of(existingUser));

        assertTrue(userService.isRepeatLogin("ivan"));
    }

    @Test
    void isRepeatLoginShouldReturnFalseWhenLoginIsFree() {
        when(userRepository.find(any(User.class))).thenReturn(Stream.empty());

        assertFalse(userService.isRepeatLogin("newUser"));
    }

    @Test
    void createUserShouldReturnGeneratedId() {
        User newUser = User.builder().login("test").build();
        when(userRepository.create(newUser)).thenReturn(42L);

        Long id = userService.createUser(newUser);

        assertEquals(42L, id);
        verify(userRepository).create(newUser);
    }

    @Test
    void deleteUserShouldDelegateToRepository() {
        User user = new User();
        userService.deleteUser(user);

        verify(userRepository).delete(user);
    }

    @Test
    void updateUserShouldDelegateToRepository() {
        User user = new User();
        userService.updateUser(user);

        verify(userRepository).update(user);
    }
}
