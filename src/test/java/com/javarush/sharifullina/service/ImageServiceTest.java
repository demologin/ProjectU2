package com.javarush.sharifullina.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @TempDir
    Path tempDir;

    @Mock
    private HttpServletRequest request;

    @Mock
    private Part part;

    private ImageService imageService;

    @BeforeEach
    void setUp() throws Exception {
        imageService = new ImageService();
        // Подменяем PATH на временную папку @TempDir, чтобы тесты не создавали файлы в проекте
        Field pathField = ImageService.class.getDeclaredField("PATH");
        pathField.setAccessible(true);
        pathField.set(imageService, tempDir);
    }

    @Test
    void getImageShouldReturnFoundImagePathWhenFileExists() throws IOException {
        Files.createFile(tempDir.resolve("avatar_1.jpg"));

        String result = imageService.getImage("avatar_1");

        assertEquals("/images/avatar_1.jpg", result);
    }

    @Test
    void getImageShouldReturnDefaultImageWhenFileDoesNotExist() {
        String result = imageService.getImage("non_existent_user");

        assertEquals("/images/default_image.png", result);
    }

    @Test
    void uploadImageShouldSaveFileAndDeleteOldExtensions() throws Exception {
        // Создаём старый файл с другим расширением
        Path oldFile = tempDir.resolve("quest_5.png");
        Files.createFile(oldFile);

        byte[] content = "fake image content".getBytes(StandardCharsets.UTF_8);
        when(request.getPart("image")).thenReturn(part);
        when(part.getSize()).thenReturn((long) content.length);
        when(part.getSubmittedFileName()).thenReturn("photo.JPEG");
        when(part.getInputStream()).thenReturn(new ByteArrayInputStream(content));

        String savedFilename = imageService.uploadImage(request, "quest_5");

        assertEquals("quest_5.jpeg", savedFilename);
        assertFalse(Files.exists(oldFile), "Старый файл должен быть удалён");
        assertTrue(Files.exists(tempDir.resolve("quest_5.jpeg")), "Новый файл должен быть сохранён");
        assertEquals("fake image content", Files.readString(tempDir.resolve("quest_5.jpeg")));
    }

    @Test
    void uploadImageShouldUseDefaultPngExtensionWhenSubmittedNameHasNoDot() throws Exception {
        byte[] content = "data".getBytes(StandardCharsets.UTF_8);
        when(request.getPart("image")).thenReturn(part);
        when(part.getSize()).thenReturn((long) content.length);
        when(part.getSubmittedFileName()).thenReturn("filename_without_extension");
        when(part.getInputStream()).thenReturn(new ByteArrayInputStream(content));

        String savedFilename = imageService.uploadImage(request, "user_2");

        assertEquals("user_2.png", savedFilename);
        assertTrue(Files.exists(tempDir.resolve("user_2.png")));
    }

    @Test
    void uploadImageShouldReturnNullWhenPartIsNull() throws Exception {
        when(request.getPart("image")).thenReturn(null);

        String result = imageService.uploadImage(request, "user_3");

        assertNull(result);
    }

    @Test
    void uploadImageShouldReturnNullWhenPartIsEmpty() throws Exception {
        when(request.getPart("image")).thenReturn(part);
        when(part.getSize()).thenReturn(0L);

        String result = imageService.uploadImage(request, "user_3");

        assertNull(result);
    }

    @Test
    void uploadImageShouldThrowRuntimeExceptionWhenServletExceptionOccurs() throws Exception {
        when(request.getPart("image")).thenThrow(new ServletException("Multipart error"));

        assertThrows(RuntimeException.class, () -> imageService.uploadImage(request, "user_4"));
    }
}
