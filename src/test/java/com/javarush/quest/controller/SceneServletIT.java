package com.javarush.quest.controller;

import com.javarush.quest.BaseIT;
import com.javarush.quest.config.constant.Schema.*;
import com.javarush.quest.controller.base.Response;
import com.javarush.quest.controller.base.Response.Redirect;
import com.javarush.quest.entity.Scene;
import com.javarush.quest.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SceneServletIT extends BaseIT {
    SceneServlet sceneServlet;
    GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        sceneServlet = context.getBean(SceneServlet.class);
        gameRepository = context.getBean(GameRepository.class);
        gameRepository.save(testGame);
        testUser.getGames().add(testGame);
    }

    @Test
    @DisplayName("When get scene then forward to JSP")
    void whenGetSceneThenForwardToJsp() {
        when(request.getParameter(Key.ID)).thenReturn("ship-0");
        Response actual = sceneServlet.handleGet(request);
        assertEquals(Response.JSP, actual);
        verify(request).setAttribute(eq(Key.SCENE), any(Scene.class));
    }

    @Test
    @DisplayName("When post scene then redirect to get scene")
    void whenPostSceneThenRedirectToGetScene() {
        when(request.getAttribute(Key.USER)).thenReturn(testUser);
        when(request.getParameter(Key.ID)).thenReturn("ship-0");
        Map<String, String> queryParams = new LinkedHashMap<>();
        queryParams.put(Key.ID, "ship-0");

        Response expected = new Redirect(Url.SCENE, queryParams);
        Response actual = sceneServlet.handlePost(request);
        assertEquals(expected, actual);
    }
}
