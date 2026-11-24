package com.javarush.sharifullina.cmd;

import com.javarush.sharifullina.entity.User;
import com.javarush.sharifullina.service.UserService;
import com.javarush.sharifullina.utils.Constant;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListUserTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ListUser listUser;

    @Test
    void doGetShouldSetUsersAttributeAndReturnListUserView() {
        List<User> users = List.of(new User(), new User());
        when(userService.getAll()).thenReturn(users);

        String view = listUser.doGet(request);

        assertEquals("list-user", view);
        verify(request).setAttribute(Constant.USERS, users);
    }
}
