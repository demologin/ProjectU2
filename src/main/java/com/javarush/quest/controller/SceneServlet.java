package com.javarush.quest.controller;

import com.javarush.quest.config.annotation.Servlet;
import com.javarush.quest.config.constant.Schema.Key;
import com.javarush.quest.config.constant.Schema.Url;
import com.javarush.quest.controller.base.BaseServlet;
import com.javarush.quest.controller.base.Response;
import com.javarush.quest.service.SceneService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Servlet(Url.SCENE)
@RequiredArgsConstructor
public class SceneServlet extends BaseServlet {
    private final SceneService sceneService;

    @Override
    protected Response handleGet(HttpServletRequest req) {
        String sceneId = req.getParameter(Key.ID);
        req.setAttribute(Key.SCENE, sceneService.getScene(sceneId));
        return Response.DEFAULT;
    }

    @Override
    protected Response handlePost(HttpServletRequest req) {
        //todo game service
        String sceneId = req.getParameter(Key.ID);
        return Response.redirect(Url.SCENE).withParam(Key.ID, sceneId);
    }
}
