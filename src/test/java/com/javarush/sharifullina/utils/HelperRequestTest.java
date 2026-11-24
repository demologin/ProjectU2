package com.javarush.sharifullina.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HelperRequestTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Test
    void constructorShouldThrowIllegalStateException() throws Exception {
        Constructor<HelperRequest> constructor = HelperRequest.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertInstanceOf(IllegalStateException.class, exception.getCause());
    }

    @Test
    void setErrorShouldPutErrorMessageIntoSession() {
        when(request.getSession()).thenReturn(session);

        HelperRequest.setError(request, "Тестовая ошибка");

        verify(session).setAttribute(Constant.ERROR_MESSAGE, "Тестовая ошибка");
    }

    @Test
    void getIdShouldReturnNullWhenRequestIsNull() {
        assertNull(HelperRequest.getId(null));
    }

    @Test
    void getIdShouldReturnNullWhenSessionIsNull() {
        when(request.getSession(false)).thenReturn(null);

        assertNull(HelperRequest.getId(request));
    }

    @Test
    void getIdShouldReturnLongWhenUserIdIsLong() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(42L);

        assertEquals(42L, HelperRequest.getId(request));
    }

    @Test
    void getIdShouldConvertToLongWhenUserIdIsInteger() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(15);

        assertEquals(15L, HelperRequest.getId(request));
    }

    @Test
    void getIdShouldReturnNullWhenUserIdIsNotANumber() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn("not_a_number");

        assertNull(HelperRequest.getId(request));
    }
}
