package com.javarush.quest.controller;

import com.javarush.quest.BaseIT;
import com.javarush.quest.config.constant.Schema.Key;
import com.javarush.quest.config.constant.Schema.Url;
import com.javarush.quest.controller.base.Response;
import com.javarush.quest.controller.base.Response.Redirect;
import com.javarush.quest.exception.impl.EntityNotFoundException;
import com.javarush.quest.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class QuestServletIT extends BaseIT {
    QuestServlet questServlet;
    GameRepository gameRepository;
    Map<String, String> queryParams;

    @BeforeEach
    void setUp() {
        questServlet = context.getBean(QuestServlet.class);
        gameRepository = context.getBean(GameRepository.class);

        queryParams = new LinkedHashMap<>();
        queryParams.put(Key.ID, testGame.getCurrentSceneId());
        when(request.getAttribute(Key.USER)).thenReturn(testUser);
        when(request.getParameter(Key.ID)).thenReturn("ship");
    }

    @Test
    @DisplayName("When game is present then load scene")
    void whenGameIsPresentThenLoadScene() {
        testUser.getGames().add(testGame);
        gameRepository.save(testGame);
        Response expected = new Redirect(Url.SCENE, queryParams);
        Response actual = questServlet.handleGet(request);

        assertEquals(expected, actual);
        verify(request, never()).setAttribute(eq(Key.QUEST), any());
    }

    @Test
    @DisplayName("When game not present then show jsp")
    void whenGameNotPresentThenShowJsp() {
        assertEquals(Response.JSP, questServlet.handleGet(request));
        verify(request).setAttribute(eq(Key.QUEST), any());
    }

    @Test
    @DisplayName("When quest not found then throw")
    void whenQuestNotFoundThenThrow() {
        when(request.getParameter(Key.ID)).thenReturn("invalid");
        assertThrows(EntityNotFoundException.class, () -> questServlet.handleGet(request));
    }

    @Test
    @DisplayName("When start quest then redirect to start scene")
    void whenStartQuestThenRedirectToStartScene() {
        Response expected = new Redirect(Url.SCENE, queryParams);
        Response actual = questServlet.handlePost(request);
        assertEquals(expected, actual);
    }
}
