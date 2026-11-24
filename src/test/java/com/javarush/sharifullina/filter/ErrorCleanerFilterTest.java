package com.javarush.sharifullina.filter;

import com.javarush.sharifullina.utils.Constant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ErrorCleanerFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @Mock
    private HttpSession session;

    private final ErrorCleanerFilter filter = new ErrorCleanerFilter();

    @Test
    void doFilterShouldMoveErrorFromSessionToRequestOnGetRequest() throws Exception {
        when(request.getRequestURI()).thenReturn("/home");
        when(request.getMethod()).thenReturn("GET");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(Constant.ERROR_MESSAGE)).thenReturn("Ошибка входа");

        filter.doFilter(request, response, chain);

        verify(request).setAttribute(Constant.ERROR_MESSAGE, "Ошибка входа");
        verify(session).removeAttribute(Constant.ERROR_MESSAGE);
        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilterShouldIgnoreStaticResourcesAndKeepErrorInSession() throws Exception {
        when(request.getRequestURI()).thenReturn("/css/style.css");

        filter.doFilter(request, response, chain);

        verify(request, never()).getSession(anyBoolean());
        verify(request, never()).setAttribute(eq(Constant.ERROR_MESSAGE), any());
        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilterShouldIgnorePostRequestsAndKeepErrorInSession() throws Exception {
        when(request.getRequestURI()).thenReturn("/log-in");
        when(request.getMethod()).thenReturn("POST");

        filter.doFilter(request, response, chain);

        verify(request, never()).getSession(anyBoolean());
        verify(chain).doFilter(request, response);
    }
}
