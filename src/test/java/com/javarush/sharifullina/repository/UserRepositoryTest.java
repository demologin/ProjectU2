package com.javarush.sharifullina.repository;

import com.javarush.sharifullina.entity.Role;
import com.javarush.sharifullina.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        BaseRepository.id.set(0L);
        userRepository = new UserRepository();

        userRepository.create(User.builder()
                .login("ivan")
                .password("1234")
                .role(Role.USER)
                .name("Иван")
                .surname("Иванов")
                .build());

        userRepository.create(User.builder()
                .login("admin")
                .password("adminpass")
                .role(Role.ADMIN)
                .name("Анна")
                .surname("Петрова")
                .build());
        userRepository.create(User.builder()
                .login("lola")
                .password("qwerty")
                .role(Role.GUEST)
                .name("Лариса")
                .surname("Локтева")
                .build());
    }

    @Test
    void findShouldReturnMatchingUsersByLoginAndPassword() {
        User pattern = User.builder()
                .login("ivan")
                .password("1234")
                .build();

        List<User> found = userRepository.find(pattern).toList();

        assertEquals(1, found.size());
        assertEquals("Иван", found.get(0).getName());
    }

    @Test
    void findShouldFilterByRole() {
        User pattern = User.builder()
                .role(Role.ADMIN)
                .build();

        List<User> found = userRepository.find(pattern).toList();

        assertEquals(1, found.size());
        assertEquals("admin", found.get(0).getLogin());
    }

    @Test
    void findShouldReturnAllUsersWhenPatternFieldsAreNull() {
        User emptyPattern = User.builder().build();

        List<User> found = userRepository.find(emptyPattern).toList();

        assertEquals(3, found.size());
    }

    @Test
    void findShouldReturnEmptyStreamWhenNoMatchFound() {
        User pattern = User.builder()
                .login("ivan")
                .password("wrong_password")
                .build();

        List<User> found = userRepository.find(pattern).toList();

        assertTrue(found.isEmpty());
    }
}

