package com.javarush.quest.controller;

import com.javarush.quest.BaseIT;
import com.javarush.quest.config.constant.Schema.Jsp;
import com.javarush.quest.config.constant.Schema.Key;
import com.javarush.quest.controller.base.Response;
import com.javarush.quest.controller.base.Response.Forward;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HomeServletIT extends BaseIT {

    @Test
    @DisplayName("When request home then forward to jsp")
    void whenRequestHomeThenForwardToJsp() {
        Response expected = new Forward(Jsp.HOME, true);
        HomeServlet homeServlet = context.getBean(HomeServlet.class);
        Response actual = homeServlet.handleGet(request);

        assertEquals(expected, actual);
        verify(request).setAttribute(eq(Key.QUESTS), any(Collection.class));
    }
}
