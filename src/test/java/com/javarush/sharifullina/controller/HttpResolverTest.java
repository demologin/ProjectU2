package com.javarush.sharifullina.controller;

import com.javarush.sharifullina.cmd.Command;
import com.javarush.sharifullina.cmd.Home;
import com.javarush.sharifullina.cmd.PrivateProfileEdit;
import com.javarush.sharifullina.config.Winter;
import com.javarush.sharifullina.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HttpResolverTest {

    @Mock
    private HttpServletRequest request;

    private final HttpResolver resolver = new HttpResolver();

    @Test
    void resolveShouldReturnHomeCommandWhenUriEndsWithSlash() {
        Home mockHome = mock(Home.class);
        when(request.getRequestURI()).thenReturn("/");

        try (MockedStatic<Winter> winterMock = mockStatic(Winter.class)) {
            winterMock.when(() -> Winter.getBean(Home.class)).thenReturn(mockHome);

            Command command = resolver.resolve(request);

            assertSame(mockHome, command);
        }
    }

    @Test
    void resolveShouldConvertKebabCaseUriToCamelCaseClassAndIgnoreQueryParams() {
        PrivateProfileEdit mockEdit = mock(PrivateProfileEdit.class);
        when(request.getRequestURI()).thenReturn("/private-profile-edit?id=5");

        try (MockedStatic<Winter> winterMock = mockStatic(Winter.class)) {
            winterMock.when(() -> Winter.getBean(PrivateProfileEdit.class)).thenReturn(mockEdit);

            Command command = resolver.resolve(request);

            assertSame(mockEdit, command);
        }
    }

    @Test
    void resolveShouldThrowAppExceptionWhenCommandClassNotFound() {
        when(request.getRequestURI()).thenReturn("/non-existent-command");

        assertThrows(AppException.class, () -> resolver.resolve(request));
    }
}
