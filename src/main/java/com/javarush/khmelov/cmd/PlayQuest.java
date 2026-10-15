package com.javarush.khmelov.cmd;

import com.javarush.khmelov.entity.*;
import com.javarush.khmelov.repository.AnswerRepository;
import com.javarush.khmelov.repository.GameRepository;
import com.javarush.khmelov.service.QuestService;
import com.javarush.khmelov.util.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class PlayQuest implements Command {

    private final QuestService questService;
    private final AnswerRepository answerRepository;
    private final GameRepository gameRepository;

    public PlayQuest(QuestService questService,AnswerRepository answerRepository, GameRepository gameRepository) {
        this.questService = questService;
        this.answerRepository = answerRepository;
        this.gameRepository = gameRepository;
    }

    public String doGet(HttpServletRequest req) {
        Game game;
        Quest quest = getQuest(req);
        Question nextQuestion;
        if (isNewGame(req)) {
            game = initGame(req);
            nextQuestion = quest.getFirstQuestion();
        } else {
            game = getGame(req);
            Answer answer = getAnswer(req);
            game.setState(answer.getState());
            gameRepository.update(game);
            nextQuestion = getNextQuestion(req);
        }
        req.setAttribute(Parameter.GAME, game);
        req.setAttribute(Parameter.QUEST, quest);
        req.setAttribute(Parameter.QUESTION, nextQuestion);
        return getView();
    }

    private boolean isNewGame(HttpServletRequest req) {
        return req.getParameter(Parameter.GAME_ID) == null;
    }

    private Quest getQuest(HttpServletRequest req) {
        long questId = Long.parseLong(req.getParameter(Parameter.QUEST_ID));
        return questService.get(questId).get();
    }

    private Question getNextQuestion(HttpServletRequest req) {
        Quest quest;
        Question nextQuestion;
        quest = getQuest(req);
        String stringNextQuestionId = req.getParameter(Parameter.QUESTION_ID);
        long questionId = Long.parseLong(stringNextQuestionId);
        nextQuestion = quest.getNextQuestion(questionId);
        return nextQuestion;
    }

    private Answer getAnswer(HttpServletRequest req) {
        long answerId = Long.parseLong(req.getParameter(Parameter.ANSWER_ID));
        return answerRepository.get(answerId).get();
    }

    private Game getGame(HttpServletRequest req) {
        String stringGameId = req.getParameter(Parameter.GAME_ID);
        if (stringGameId == null) {
            return null;
        }
        long gameId = Long.parseLong(stringGameId);
        return gameRepository.get(gameId).get();
    }

    private Game initGame(HttpServletRequest req) {
        Quest quest = getQuest(req);
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");
        Game game = new Game(user, quest, State.PLAY);
        gameRepository.create(game);
        return game;
    }
}
