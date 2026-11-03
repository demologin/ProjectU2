package com.javarush.zimina.cmd;

import com.javarush.zimina.entity.GameState;
import com.javarush.zimina.entity.Question;
import com.javarush.zimina.entity.User;
import com.javarush.zimina.service.GameService;
import com.javarush.zimina.service.StatisticsService;
import com.javarush.zimina.util.Go;
import com.javarush.zimina.util.Key;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class PlayGame implements Command {
    private final GameService gameService;
    private final StatisticsService statisticsService;

    public PlayGame(GameService gameService, StatisticsService statisticsService) {
        this.gameService = gameService;
        this.statisticsService = statisticsService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String questId = request.getParameter(Key.QUEST_ID);
        Question question;
        if (questId != null) {
            question = gameService.start(Long.valueOf(questId));
            if (question == null) {
                return getView();
            }
            session.setAttribute(Key.QUEST_ID, Long.valueOf(questId));
        } else {
            Long questionId = (Long) session.getAttribute(Key.QUESTION_ID);
            if (questionId == null) {
                return getView();
            }
            question = gameService.getQuestion(questionId);
        }
        session.setAttribute(Key.QUESTION_ID, question.getId());
        request.setAttribute(Key.QUESTION, question);
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long questionId = (Long) session.getAttribute(Key.QUESTION_ID);
        if (questionId == null) {
            return Go.LIST_QUEST;
        }
        int answerIndex = Integer.parseInt(request.getParameter(Key.ANSWER));
        Question next = gameService.move(questionId, answerIndex);
        session.setAttribute(Key.QUESTION_ID, next.getId());

        if (next.getGameState() != GameState.PLAY) {
            Long questId = (Long) session.getAttribute(Key.QUEST_ID);
            User user = (User) session.getAttribute(Key.USER);
            Long userId = (user == null) ? null : user.getId();
            statisticsService.record(questId, userId, next.getGameState() == GameState.WIN);
        }
        return Go.PLAY_GAME;
    }
}