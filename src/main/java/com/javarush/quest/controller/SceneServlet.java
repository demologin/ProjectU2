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

import java.util.Optional;

@Servlet(Url.SCENE)
@RequiredArgsConstructor
public class SceneServlet extends BaseServlet {
    private final SceneService sceneService;
    private final GameService gameService;

    @Override
    protected Response handleGet(HttpServletRequest request) {
        Scene scene = sceneService.getScene(request.getParameter(Key.ID));

        Long userId = Optional
                .ofNullable(request.getSession().getAttribute(Key.USER))
                .map(User.class::cast)
                .map(User::getId)
                .orElseThrow();
        gameService.saveGame(userId, scene);

        request.setAttribute(Key.SCENE, scene);
        return Response.JSP;
    }
}
