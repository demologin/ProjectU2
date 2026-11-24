package com.javarush.sharifullina.cmd;

import com.javarush.sharifullina.entity.Role;
import com.javarush.sharifullina.entity.User;
import com.javarush.sharifullina.service.UserService;
import com.javarush.sharifullina.utils.Constant;
import com.javarush.sharifullina.utils.Go;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogInTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private LogIn logIn;

    @Test
    void doPostShouldSaveUserInSessionAndRedirectHomeWhenCredentialsValid() {
        User user = User.builder()
                .id(7L)
                .login("ivan")
                .password("1234")
                .role(Role.USER)
                .build();

        when(request.getParameter(Constant.LOGIN)).thenReturn("ivan");
        when(request.getParameter(Constant.PASSWORD)).thenReturn("1234");
        when(userService.getUser("ivan", "1234")).thenReturn(user);
        when(request.getSession()).thenReturn(session);

        String view = logIn.doPost(request);

        assertEquals(Go.HOME, view);
        verify(session).setAttribute(Constant.USER_ID, 7L);
        verify(session).setAttribute(Constant.ROLE, Role.USER);
    }

    @Test
    void doPostShouldSetErrorAndReturnLogInViewWhenUserNotFound() {
        when(request.getParameter(Constant.LOGIN)).thenReturn("wrong");
        when(request.getParameter(Constant.PASSWORD)).thenReturn("badpass");
        when(userService.getUser("wrong", "badpass")).thenReturn(null);
        when(request.getSession()).thenReturn(session);

        String view = logIn.doPost(request);

        assertEquals("log-in", view);
        verify(session).setAttribute(anyString(), eq("Неправильный логин или пароль"));
        verify(session, never()).setAttribute(eq(Constant.USER_ID), any());
    }
}
