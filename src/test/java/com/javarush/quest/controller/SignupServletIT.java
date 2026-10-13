package com.javarush.quest.controller;

import com.javarush.quest.BaseIT;
import com.javarush.quest.config.constant.Schema.Key;
import com.javarush.quest.config.constant.Schema.Url;
import com.javarush.quest.controller.base.Response.Redirect;
import com.javarush.quest.entity.User;
import com.javarush.quest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class SignupServletIT extends BaseIT {
    SignupServlet signupServlet;

    @BeforeEach
    void setUp() {
        signupServlet = context.getBean(SignupServlet.class);
        when(request.getParameter(Key.LOGIN)).thenReturn("login");
        when(request.getParameter(Key.PASSWORD)).thenReturn("password");
    }

    @Test
    @DisplayName("When signup then redirect to profile")
    void whenSignupThenRedirectToProfile() {
        Redirect expected = new Redirect(Url.USERS, new LinkedHashMap<>());
        Redirect actual = (Redirect) signupServlet.handlePost(request);

        assertEquals(expected.target(), actual.target()); //exclude generated id
        verify(session).setAttribute(eq(Key.USER), any(User.class));
    }

    @Test
    @DisplayName("When signup then create new user")
    void whenSignupThenCreateNewUser() {
        signupServlet.handlePost(request);
        UserRepository userRepository = context.getBean(UserRepository.class);
        Optional<User> userOpt = userRepository.findFirstBy("login", "password");
        assertTrue(userOpt.isPresent());
    }
}
