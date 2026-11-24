package com.javarush.sharifullina.controller;

import com.javarush.sharifullina.cmd.Command;
import com.javarush.sharifullina.init.InitQuestCreator;
import com.javarush.sharifullina.init.InitUsersCreator;
import com.javarush.sharifullina.config.Winter;
import com.javarush.sharifullina.entity.Role;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FrontServletTest {

    @Mock
    private HttpResolver httpResolver;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private Command command;

    private FrontServlet frontServlet;

    @BeforeEach
    void setUp() {
        try (MockedStatic<Winter> winterMock = mockStatic(Winter.class)) {
            winterMock.when(() -> Winter.getBean(HttpResolver.class)).thenReturn(httpResolver);
            frontServlet = new FrontServlet();
        }
    }

    @Test
    void initShouldCreateInitialDataAndSetRolesInServletContext() {
        ServletConfig servletConfig = mock(ServletConfig.class);
        ServletContext servletContext = mock(ServletContext.class);
        InitUsersCreator usersCreator = mock(InitUsersCreator.class);
        InitQuestCreator questCreator = mock(InitQuestCreator.class);

        when(servletConfig.getServletContext()).thenReturn(servletContext);

        try (MockedStatic<Winter> winterMock = mockStatic(Winter.class)) {
            winterMock.when(() -> Winter.getBean(InitUsersCreator.class)).thenReturn(usersCreator);
            winterMock.when(() -> Winter.getBean(InitQuestCreator.class)).thenReturn(questCreator);

            frontServlet.init(servletConfig);

            verify(usersCreator).createUsers();
            verify(questCreator).createQuests();
            verify(servletContext).setAttribute("roles", Role.values());
        }
    }

    @Test
    void doGetShouldForwardToJspWhenViewDoesNotStartWithRedirect() throws Exception {
        when(httpResolver.resolve(request)).thenReturn(command);
        when(command.doGet(request)).thenReturn("home");
        when(request.getRequestDispatcher("WEB-INF/home.jsp")).thenReturn(requestDispatcher);

        frontServlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
        verify(response, never()).sendRedirect(anyString());
    }

    @Test
    void doGetShouldSendRedirectWhenViewStartsWithRedirectPrefix() throws Exception {
        when(httpResolver.resolve(request)).thenReturn(command);
        when(command.doGet(request)).thenReturn("redirect:list-user");

        frontServlet.doGet(request, response);

        verify(response).sendRedirect("list-user");
        verify(request, never()).getRequestDispatcher(anyString());
    }

    @Test
    void doPostShouldSendRedirectToReturnedPath() throws Exception {
        when(httpResolver.resolve(request)).thenReturn(command);
        when(command.doPost(request)).thenReturn("home");

        frontServlet.doPost(request, response);

        verify(response).sendRedirect("home");
    }
}
