package com.javarush.quest.controller;

import com.javarush.quest.BaseIT;
import com.javarush.quest.config.constant.Schema.Key;
import com.javarush.quest.controller.base.Response;
import com.javarush.quest.entity.UserStats;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProfileServletIT extends BaseIT {

    @Test
    @DisplayName("When request profile then show jsp")
    void whenRequestProfileThenShowJsp() {
        when(request.getAttribute(Key.USER)).thenReturn(testUser);
        ProfileServlet profileServlet = context.getBean(ProfileServlet.class);
        Response actual = profileServlet.handleGet(request);

        assertEquals(Response.JSP, actual);
        verify(request).setAttribute(eq(Key.USER_STATS), any(UserStats.class));
    }
}
