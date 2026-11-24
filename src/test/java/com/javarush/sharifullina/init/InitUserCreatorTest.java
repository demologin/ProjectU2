package com.javarush.sharifullina.init;

import com.javarush.sharifullina.entity.Role;
import com.javarush.sharifullina.entity.User;
import com.javarush.sharifullina.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InitUsersCreatorTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private InitUsersCreator initUsersCreator;

    @Test
    void createUsersShouldCreateAllFourDefaultUsersWithExpectedFields() {
        initUsersCreator.createUsers();

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService, times(4)).createUser(userCaptor.capture());

        List<User> createdUsers = userCaptor.getAllValues();

        User alisa = createdUsers.get(0);
        assertEquals("Alisa", alisa.getLogin());
        assertEquals("qwerty", alisa.getPassword());
        assertEquals(Role.USER, alisa.getRole());
        assertEquals(15, alisa.getScore());

        User bob = createdUsers.get(1);
        assertEquals("Bob", bob.getLogin());
        assertEquals("", bob.getPassword());
        assertEquals(Role.GUEST, bob.getRole());
        assertEquals(5, bob.getScore());

        User carl = createdUsers.get(2);
        assertEquals("Carl", carl.getLogin());
        assertEquals("admin", carl.getPassword());
        assertEquals(Role.ADMIN, carl.getRole());
        assertEquals(100, carl.getScore());

        User khmelov = createdUsers.get(3);
        assertEquals("Khmelov", khmelov.getLogin());
        assertEquals("admin", khmelov.getPassword());
        assertEquals(Role.ADMIN, khmelov.getRole());
        assertEquals(3, khmelov.getScore());
    }
}
