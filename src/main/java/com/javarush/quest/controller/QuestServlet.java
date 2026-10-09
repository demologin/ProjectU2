package com.javarush.quest.controller;

import com.javarush.quest.config.annotation.Servlet;
import com.javarush.quest.config.constant.Schema.Key;
import com.javarush.quest.config.constant.Schema.Url;
import com.javarush.quest.controller.base.BaseServlet;
import com.javarush.quest.controller.base.Response;
import com.javarush.quest.entity.Game;
import com.javarush.quest.entity.Quest;
import com.javarush.quest.entity.Scene;
import com.javarush.quest.entity.User;
import com.javarush.quest.service.GameService;
import com.javarush.quest.service.QuestService;
import com.javarush.quest.service.SceneService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Servlet(Url.QUEST)
@RequiredArgsConstructor
public class QuestServlet extends BaseServlet {
    private final QuestService questService;
    private final SceneService sceneService;
    private final GameService gameService;

    @Override
    protected Response handleGet(HttpServletRequest req) {
        Optional<User> userOpt = Optional
                .ofNullable(req.getSession().getAttribute(Key.USER))
                .map(User.class::cast);
        if (userOpt.isEmpty()) {
            return Response.redirect(Url.LOGIN);
        }

        Long userId = userOpt.get().getId();
        String questId = req.getParameter(Key.ID); //todo if questId == null
        Quest quest = questService.getQuest(questId);
        req.setAttribute(Key.QUEST, quest);

        Optional<Game> gameOpt = gameService.findGame(userId, questId);
        if (gameOpt.isEmpty()) {
            return Response.DEFAULT;
        }

        Game game = gameOpt.get();
        String sceneId = game.getCurrentSceneId();
        Scene scene = sceneService.getScene(sceneId);

        if (scene.getType() == Scene.Type.GAME_OVER) {
            gameService.deleteGame(game);
            return Response.DEFAULT;
        }
        return Response.redirect(Url.SCENE).withParam(Key.ID, sceneId);
    }

    @Override
    protected Response handlePost(HttpServletRequest req) {
        Long userId = Optional
                .ofNullable(req.getSession().getAttribute(Key.USER))
                .map(User.class::cast)
                .map(User::getId)
                .orElseThrow(); //todo some helpers?
        String questId = req.getParameter(Key.ID);
        String sceneId = questService.getQuest(questId).getStartSceneId();

        gameService.createGame(userId, questId, sceneId);
        return Response.redirect(Url.SCENE).withParam(Key.ID, sceneId);
    }
}
