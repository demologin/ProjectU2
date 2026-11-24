package com.javarush.sharifullina.cmd;

import com.javarush.sharifullina.entity.User;
import com.javarush.sharifullina.service.ImageService;
import com.javarush.sharifullina.service.UserService;
import com.javarush.sharifullina.utils.Go;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfileTest {

    @Mock
    private UserService userService;

    @Mock
    private ImageService imageService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private UserProfile userProfile;

    @Test
    void doGetShouldSetAttributesAndReturnViewWhenUserFound() {
        User user = User.builder().id(5L).login("alex").build();
        when(request.getParameter("id")).thenReturn("5");
        when(userService.getUser(5L)).thenReturn(user);
        when(imageService.getImage(any())).thenReturn("/images/avatar.png");

        String view = userProfile.doGet(request);

        assertEquals("user-profile", view);
        verify(request).setAttribute("currentUser", user);
        verify(request).setAttribute(eq("imagePath"), startsWith("/images/avatar.png?t="));
    }

    @Test
    void doGetShouldRedirectWhenIdParamIsMissingOrBlank() {
        when(request.getParameter("id")).thenReturn("   ");
        when(request.getSession()).thenReturn(session);

        String view = userProfile.doGet(request);

        assertEquals("redirect:" + Go.LIST_USER, view);
        verify(session).setAttribute(anyString(), eq("Не указан ID пользователя"));
        verifyNoInteractions(userService);
    }

    @Test
    void doGetShouldRedirectWhenIdParamIsNotANumber() {
        when(request.getParameter("id")).thenReturn("abc");
        when(request.getSession()).thenReturn(session);

        String view = userProfile.doGet(request);

        assertEquals("redirect:" + Go.LIST_USER, view);
        verify(session).setAttribute(anyString(), eq("Некорректный формат ID"));
        verifyNoInteractions(userService);
    }

    @Test
    void doGetShouldRedirectWhenUserNotFound() {
        when(request.getParameter("id")).thenReturn("99");
        when(userService.getUser(99L)).thenReturn(null);
        when(request.getSession()).thenReturn(session);

        String view = userProfile.doGet(request);

        assertEquals("redirect:" + Go.LIST_USER, view);
        verify(session).setAttribute(anyString(), eq("Пользователь c id = 99 не найден"));
    }
}
