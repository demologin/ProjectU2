package com.javarush.quest.controller;

import com.javarush.quest.BaseIT;
import com.javarush.quest.config.constant.Schema.Key;
import com.javarush.quest.controller.base.Response;
import com.javarush.quest.entity.Scene;
import com.javarush.quest.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SceneServletIT extends BaseIT {
    GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        gameRepository = context.getBean(GameRepository.class);
        gameRepository.save(testGame);
        testUser.getGames().add(testGame);
    }

    @Test
    @DisplayName("When get scene then show jsp")
    void whenGetSceneThenShowJsp() {
        when(request.getAttribute(Key.USER)).thenReturn(testUser);
        when(request.getParameter(Key.ID)).thenReturn("ship-0");

        SceneServlet sceneServlet = context.getBean(SceneServlet.class);
        Response actual = sceneServlet.handleGet(request);

        assertEquals(Response.JSP, actual);
        verify(request).setAttribute(eq(Key.SCENE), any(Scene.class));
    }
}
