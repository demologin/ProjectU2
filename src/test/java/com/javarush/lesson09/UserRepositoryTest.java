package com.javarush.lesson09;

import com.javarush.khmelov.entity.Role;
import com.javarush.khmelov.entity.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {


    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepository();
    }


    @Test
    @DisplayName("When find by id then get user id=1 role=ADMIN")
    void get() {
        User user = userRepository.get(1L);
        assertEquals(1L, user.getId());
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    @DisplayName("When create+update+delete tempUser then no Exception")
    void createUpdateDelete() {
        User tempUser = User.builder()
                .login("login_test")
                .password("password_test")
                .role(Role.GUEST)
                .build();
        userRepository.create(tempUser);
        System.out.println("CREATE " + tempUser);

        tempUser.setPassword("password_test_update");
        userRepository.update(tempUser);
        System.out.println("UPDATE " + tempUser);

        userRepository.delete(tempUser);
        System.out.println("DELETE " + tempUser);
        assertTrue(tempUser.getId() > 0);
    }

    @AfterEach
    public void destroy() throws Exception {
        userRepository.close();
    }

}