package com.javarush.sharifullina.cmd;

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
class ExitTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private Exit exit;

    @Test
    void doPostShouldRemoveUserIdFromSessionAndReturnHome() {
        when(request.getSession()).thenReturn(session);

        String result = exit.doPost(request);

        assertEquals("home", result);
        verify(session).removeAttribute("userId");
    }
}
