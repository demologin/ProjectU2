package com.javarush.quest.controller;

import com.javarush.quest.BaseIT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;

class LogoutServletIT extends BaseIT {

    @Test
    @DisplayName("When logout then invalidate session")
    void whenLogoutThenInvalidateSession() {
        LogoutServlet logoutServlet = context.getBean(LogoutServlet.class);
        logoutServlet.handleGet(request);
        verify(session).invalidate();
    }
}
