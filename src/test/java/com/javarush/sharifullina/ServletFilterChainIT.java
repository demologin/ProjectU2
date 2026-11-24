package com.javarush.sharifullina;

import com.javarush.sharifullina.config.Winter;
import com.javarush.sharifullina.controller.FrontServlet;
import com.javarush.sharifullina.filter.ErrorCleanerFilter;
import com.javarush.sharifullina.utils.Constant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ServletFilterChainIT {

    private FrontServlet frontServlet;
    private ErrorCleanerFilter errorCleanerFilter;

    private Map<String, Object> sessionStorage;
    private Map<String, Object> requestStorage;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        Winter.beans.clear();
        frontServlet = new FrontServlet();
        errorCleanerFilter = new ErrorCleanerFilter();

        ServletConfig config = mock(ServletConfig.class);
        ServletContext context = mock(ServletContext.class);
        when(config.getServletContext()).thenReturn(context);
        frontServlet.init(config);

        sessionStorage = new HashMap<>();
        requestStorage = new HashMap<>();
        session = mock(HttpSession.class);

        doAnswer(inv -> {
            sessionStorage.put(inv.getArgument(0), inv.getArgument(1));
            return null;
        }).when(session).setAttribute(anyString(), any());

        when(session.getAttribute(anyString())).thenAnswer(inv -> sessionStorage.get(inv.getArgument(0)));

        doAnswer(inv -> {
            sessionStorage.remove(inv.getArgument(0));
            return null;
        }).when(session).removeAttribute(anyString());
    }

    @Test
    void postLoginFailureShouldPersistErrorAcrossRedirectAndClearItOnNextGet() throws Exception {
        // 1. Имитируем POST /log-in с неверным паролем
        HttpServletRequest postReq = createMockRequest("/log-in", "POST");
        HttpServletResponse postRes = mock(HttpServletResponse.class);
        when(postReq.getParameter(Constant.LOGIN)).thenReturn("Alisa");
        when(postReq.getParameter(Constant.PASSWORD)).thenReturn("wrong_password");

        FilterChain postChain = (req, res) -> frontServlet.service(req, res);
        errorCleanerFilter.doFilter(postReq, postRes, postChain);

        // Проверяем редирект и наличие ошибки в сессии
        verify(postRes).sendRedirect("log-in");
        assertEquals("Неправильный логин или пароль", sessionStorage.get(Constant.ERROR_MESSAGE));

        // 2. Имитируем последующий GET /log-in после редиректа
        requestStorage.clear();
        HttpServletRequest getReq = createMockRequest("/log-in", "GET");
        HttpServletResponse getRes = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(getReq.getRequestDispatcher("WEB-INF/log-in.jsp")).thenReturn(dispatcher);

        FilterChain getChain = (req, res) -> frontServlet.service(req, res);
        errorCleanerFilter.doFilter(getReq, getRes, getChain);

        // Ошибка перенесена в request для показа на странице и удалена из сессии
        assertEquals("Неправильный логин или пароль", requestStorage.get(Constant.ERROR_MESSAGE));
        assertNull(sessionStorage.get(Constant.ERROR_MESSAGE));
        verify(dispatcher).forward(getReq, getRes);
    }

    private HttpServletRequest createMockRequest(String uri, String method) {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getRequestURI()).thenReturn(uri);
        when(req.getMethod()).thenReturn(method);
        when(req.getSession()).thenReturn(session);
        when(req.getSession(false)).thenReturn(session);

        doAnswer(inv -> {
            requestStorage.put(inv.getArgument(0), inv.getArgument(1));
            return null;
        }).when(req).setAttribute(anyString(), any());

        return req;
    }
}
