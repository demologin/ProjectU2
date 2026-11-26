package com.javarush.quest.controller;

import com.javarush.quest.config.annotation.Servlet;
import com.javarush.quest.config.constant.Schema.*;
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
        String sceneId = request.getParameter(Key.ID);
        Scene scene = sceneService.getScene(sceneId);
        request.setAttribute(Key.SCENE, scene);
        return Response.JSP;
    }

    @Override
    protected Response handlePost(HttpServletRequest request) {
        Long userId = ((User) request.getAttribute(Key.USER)).getId();
        String sceneId = request.getParameter(Key.ID);
        Scene scene = sceneService.getScene(sceneId);

        gameService.saveGame(userId, scene);
        return Response.redirect(Url.SCENE).withParam(Key.ID, sceneId);
    }
}
