package com.javarush.sharifullina.cmd;

import com.javarush.sharifullina.entity.Role;
import com.javarush.sharifullina.entity.User;
import com.javarush.sharifullina.service.ImageService;
import com.javarush.sharifullina.service.UserService;
import com.javarush.sharifullina.utils.Go;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrivateProfileEditTest {

    @Mock
    private UserService userService;

    @Mock
    private ImageService imageService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private PrivateProfileEdit privateProfileEdit;

    @BeforeEach
    void setUp() {
        when(request.getSession()).thenReturn(session);
    }

    // ==================== Тесты doGet ====================

    @Test
    void doGetShouldSetAttributesAndReturnViewWhenUserExists() {
        User user = User.builder().id(3L).login("maria").build();
        when(session.getAttribute("userId")).thenReturn(3L);
        when(userService.getUser(3L)).thenReturn(user);
        when(imageService.getImage(user.getImage())).thenReturn("/images/image-3.png");

        String view = privateProfileEdit.doGet(request);

        assertEquals("private-profile-edit", view);
        verify(request).setAttribute("enteredUser", user);
        verify(request).setAttribute(eq("imagePath"), startsWith("/images/image-3.png?t="));
    }

    @Test
    void doGetShouldRedirectHomeWhenUserNotFound() {
        when(session.getAttribute("userId")).thenReturn(99L);
        when(userService.getUser(99L)).thenReturn(null);

        String view = privateProfileEdit.doGet(request);

        assertEquals("redirect:" + Go.HOME, view);
        verify(session).setAttribute(eq("errorMessage"), eq("Пользователь с id = 99 не найден"));
        verifyNoInteractions(imageService);
    }

    // ==================== Тесты doPost ====================

    @Test
    void doPostShouldUpdateUserFieldsUploadImageAndReturnView() throws Exception {
        User user = User.builder().id(3L).build();
        when(session.getAttribute("userId")).thenReturn(3L);
        when(userService.getUser(3L)).thenReturn(user);

        mockMultipartField("birthday", "1995-05-15");
        mockMultipartField("role", "ADMIN");
        mockMultipartField("name", "Мария");
        mockMultipartField("login", "maria_new");
        mockMultipartField("surname", "Смирнова");

        String view = privateProfileEdit.doPost(request);

        assertEquals("private-profile-edit", view);
        assertEquals(LocalDate.of(1995, 5, 15), user.getBirthday());
        assertEquals(Role.ADMIN, user.getRole());
        assertEquals("Мария", user.getName());
        assertEquals("maria_new", user.getLogin());
        assertEquals("Смирнова", user.getSurname());

        verify(userService).updateUser(user);
        verify(imageService).uploadImage(request, user.getImage());
        verify(session).setAttribute("role", Role.ADMIN);
    }

    @Test
    void doPostShouldHandleEmptyBirthdayPartAsNull() throws Exception {
        User user = User.builder().id(3L).build();
        when(session.getAttribute("userId")).thenReturn(3L);
        when(userService.getUser(3L)).thenReturn(user);

        // Пустое значение даты рождения -> getParameter вернёт null
        mockMultipartField("birthday", "   ");
        mockMultipartField("role", "USER");
        mockMultipartField("name", "Иван");
        mockMultipartField("login", "ivan");
        mockMultipartField("surname", "Петров");

        String view = privateProfileEdit.doPost(request);

        assertEquals("private-profile-edit", view);
        assertNull(user.getBirthday());
        verify(userService).updateUser(user);
    }

    @Test
    void doPostShouldSetErrorAndReturnViewWhenServletExceptionOccurs() throws Exception {
        when(request.getPart("birthday")).thenThrow(new ServletException("Multipart error"));

        String view = privateProfileEdit.doPost(request);

        assertEquals("private-profile-edit", view);
        verify(session).setAttribute(anyString(), eq("Поля 'birthday' не существует"));
        verifyNoInteractions(userService);
        verifyNoInteractions(imageService);
    }

    // Вспомогательный метод для мокирования multipart-поля формы
    private void mockMultipartField(String name, String value) throws IOException, ServletException {
        Part part = mock(Part.class);
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        when(request.getPart(name)).thenReturn(part);
        when(part.getSize()).thenReturn((long) bytes.length);
        when(part.getInputStream()).thenReturn(new ByteArrayInputStream(bytes));
    }
}
