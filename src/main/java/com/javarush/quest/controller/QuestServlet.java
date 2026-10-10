package com.javarush.quest.controller;

import com.javarush.quest.config.annotation.Servlet;
import com.javarush.quest.config.constant.Schema.Key;
import com.javarush.quest.config.constant.Schema.Url;
import com.javarush.quest.controller.base.BaseServlet;
import com.javarush.quest.controller.base.Response;
import com.javarush.quest.entity.Game;
import com.javarush.quest.entity.User;
import com.javarush.quest.service.GameService;
import com.javarush.quest.service.QuestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Servlet(Url.QUEST)
@RequiredArgsConstructor
public class QuestServlet extends BaseServlet {
    private final QuestService questService;
    private final GameService gameService;

    @Override
    protected Response handleGet(HttpServletRequest request) {
        Long userId = ((User) request.getAttribute(Key.USER)).getId();
        String questId = request.getParameter(Key.ID);
        Optional<Game> game = gameService.loadGame(userId, questId);

        if (game.isPresent()) {
            String sceneId = game.get().getCurrentSceneId();
            return Response.redirect(Url.SCENE).withParam(Key.ID, sceneId);
        }
        request.setAttribute(Key.QUEST, questService.getQuest(questId));
        return Response.JSP;
    }

    @Override
    protected Response handlePost(HttpServletRequest request) {
        Long userId = ((User) request.getAttribute(Key.USER)).getId();
        String questId = request.getParameter(Key.ID);
        String sceneId = questService.getQuest(questId).getStartSceneId();

        gameService.createGame(userId, questId, sceneId);
        return Response.redirect(Url.SCENE).withParam(Key.ID, sceneId);
    }
}
