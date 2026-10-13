package com.javarush.quest.controller;

import com.javarush.quest.BaseIT;
import com.javarush.quest.config.constant.Schema.Key;
import com.javarush.quest.config.constant.Schema.Url;
import com.javarush.quest.controller.base.Response;
import com.javarush.quest.controller.base.Response.Redirect;
import com.javarush.quest.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoginServletIT extends BaseIT {

    @Test
    @DisplayName("When login then redirect to homepage")
    void whenLoginThenRedirectToHomepage() {
        when(request.getParameter("login")).thenReturn("Carl");
        when(request.getParameter("password")).thenReturn("admin");
        Response expected = new Redirect(Url.HOME, new LinkedHashMap<>());

        LoginServlet loginServlet = context.getBean(LoginServlet.class);
        Response actual = loginServlet.handlePost(request);

        assertEquals(expected, actual);
        verify(session).setAttribute(eq(Key.USER), any(User.class));
    }
}
