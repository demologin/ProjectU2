package com.javarush.quest.controller;

import com.javarush.quest.config.annotation.Servlet;
import com.javarush.quest.config.constant.Schema.Key;
import com.javarush.quest.config.constant.Schema.Url;
import com.javarush.quest.controller.base.BaseServlet;
import com.javarush.quest.controller.base.Response;
import com.javarush.quest.entity.Scene;
import com.javarush.quest.entity.User;
import com.javarush.quest.service.GameService;
import com.javarush.quest.service.SceneService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Servlet(Url.SCENE)
@RequiredArgsConstructor
public class SceneServlet extends BaseServlet {
    private final SceneService sceneService;
    private final GameService gameService;

    @Override
    protected Response handleGet(HttpServletRequest request) {
        Long userId = ((User) request.getAttribute(Key.USER)).getId();
        Scene scene = sceneService.getScene(request.getParameter(Key.ID));
        gameService.saveGame(userId, scene);
        request.setAttribute(Key.SCENE, scene);
        return Response.JSP;
    }
}
