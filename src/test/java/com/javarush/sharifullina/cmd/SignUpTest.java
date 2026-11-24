package com.javarush.sharifullina.cmd;

import com.javarush.sharifullina.entity.Role;
import com.javarush.sharifullina.entity.User;
import com.javarush.sharifullina.service.UserService;
import com.javarush.sharifullina.utils.Constant;
import com.javarush.sharifullina.utils.Go;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignUpTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private SignUp signUp;

    @BeforeEach
    void setUp() {
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void doPostShouldReturnSignUpWhenFieldsAreEmpty() {
        when(request.getParameter(Constant.LOGIN)).thenReturn("");
        when(request.getParameter(Constant.PASSWORD_FIRST)).thenReturn("1234");
        when(request.getParameter(Constant.PASSWORD_SECOND)).thenReturn("1234");

        String view = signUp.doPost(request);

        assertEquals(Go.SIGN_UP, view);
        verify(session).setAttribute(anyString(), eq("Заполните поля"));
        verify(userService, never()).createUser(any());
    }

    @Test
    void doPostShouldReturnSignUpWhenLoginAlreadyExists() {
        when(request.getParameter(Constant.LOGIN)).thenReturn("existingUser");
        when(request.getParameter(Constant.PASSWORD_FIRST)).thenReturn("1234");
        when(request.getParameter(Constant.PASSWORD_SECOND)).thenReturn("1234");
        when(userService.isRepeatLogin("existingUser")).thenReturn(true);

        String view = signUp.doPost(request);

        assertEquals(Go.SIGN_UP, view);
        verify(session).setAttribute(anyString(), eq("Логин уже существует"));
        verify(userService, never()).createUser(any());
    }

    @Test
    void doPostShouldReturnSignUpWhenPasswordsDoNotMatch() {
        when(request.getParameter(Constant.LOGIN)).thenReturn("newUser");
        when(request.getParameter(Constant.PASSWORD_FIRST)).thenReturn("1234");
        when(request.getParameter(Constant.PASSWORD_SECOND)).thenReturn("5678");
        when(userService.isRepeatLogin("newUser")).thenReturn(false);

        String view = signUp.doPost(request);

        assertEquals(Go.SIGN_UP, view);
        verify(session).setAttribute(anyString(), eq("Введены разные пароли"));
        verify(userService, never()).createUser(any());
    }

    @Test
    void doPostShouldCreateUserAndRedirectToProfileEditWhenValid() {
        when(request.getParameter(Constant.LOGIN)).thenReturn("newUser");
        when(request.getParameter(Constant.PASSWORD_FIRST)).thenReturn("secret");
        when(request.getParameter(Constant.PASSWORD_SECOND)).thenReturn("secret");
        when(userService.isRepeatLogin("newUser")).thenReturn(false);
        when(userService.createUser(any(User.class))).thenReturn(15L);

        String view = signUp.doPost(request);

        assertEquals(Go.PRIVATE_PROFILE_EDIT, view);
        verify(session).setAttribute("userId", 15L);
        verify(session).setAttribute("role", Role.USER);
    }
}
