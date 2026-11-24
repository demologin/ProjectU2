package com.javarush.sharifullina.cmd;

import com.javarush.sharifullina.entity.Quest;
import com.javarush.sharifullina.exception.CreateGameException;
import com.javarush.sharifullina.service.CreateQuestService;
import com.javarush.sharifullina.service.ImageService;
import com.javarush.sharifullina.service.QuestService;
import com.javarush.sharifullina.utils.Go;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateQuestTest {

    @Mock
    private CreateQuestService createQuestService;

    @Mock
    private QuestService questService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private CreateQuest createQuest;

    @AfterEach
    void tearDown() throws Exception {
        Path createdFile = Paths.get(ImageService.class.getResource("/").toURI())
                .getParent()
                .resolve("textfiles")
                .resolve("quest-10.txt");
        Files.deleteIfExists(createdFile);
    }

    @Test
    void doPostShouldCreateQuestSaveFileAndRedirectHome() throws Exception {
        String questText = "Новый квест\n?1 Вопрос";
        Quest quest = new Quest();
        quest.setId(10L);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("new-quest-text")).thenReturn(questText);
        when(createQuestService.createQuest(questText)).thenReturn(quest);

        String result = createQuest.doPost(request);

        assertEquals(Go.HOME, result);
        verify(questService).create(quest);

        Path savedFile = Paths.get(ImageService.class.getResource("/").toURI())
                .getParent()
                .resolve("textfiles")
                .resolve("quest-10.txt");
        assertTrue(Files.exists(savedFile));
        assertEquals(questText, Files.readString(savedFile));
    }

    @Test
    void doPostShouldSetErrorAndReturnViewWhenCreateGameExceptionThrown() {
        String invalidText = "Невалидный текст";
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("new-quest-text")).thenReturn(invalidText);
        when(createQuestService.createQuest(invalidText))
                .thenThrow(new CreateGameException("Ошибка парсинга"));

        String result = createQuest.doPost(request);

        assertEquals("create-quest", result);
        verify(session).setAttribute("savedQuestText", invalidText);
        verify(session).setAttribute(anyString(), eq("Ошибка парсинга"));
        verify(questService, never()).create(any());
    }

}
