package com.javarush.zimina.cmd;

import com.javarush.zimina.entity.User;
import com.javarush.zimina.repository.UserRepository;
import com.javarush.zimina.service.UserService;
import com.javarush.zimina.util.Go;
import com.javarush.zimina.util.Key;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoginTest {

    private HttpServletRequest request;
    private HttpSession session;
    private Login login;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        UserService userService = new UserService(new UserRepository());
        userService.register("maria", "secret");
        login = new Login(userService);
    }

    @Test
    void correctPasswordPutsUserInSession() {
        when(request.getParameter(Key.LOGIN)).thenReturn("maria");
        when(request.getParameter(Key.PASSWORD)).thenReturn("secret");

        String redirect = login.doPost(request);

        assertEquals(Go.HOME, redirect);
        verify(session).setAttribute(eq(Key.USER), any(User.class));
    }

    @Test
    void wrongPasswordReturnsToLogin() {
        when(request.getParameter(Key.LOGIN)).thenReturn("maria");
        when(request.getParameter(Key.PASSWORD)).thenReturn("wrong");

        String redirect = login.doPost(request);

        assertEquals(Go.LOGIN + "?error=wrong", redirect);
        verify(session, never()).setAttribute(anyString(), any());
    }
}