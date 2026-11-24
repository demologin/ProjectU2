package com.javarush.sharifullina.cmd;

import com.javarush.sharifullina.entity.*;
import com.javarush.sharifullina.exception.AppException;
import com.javarush.sharifullina.service.QuestService;
import com.javarush.sharifullina.service.UserService;
import com.javarush.sharifullina.utils.Go;
import com.javarush.sharifullina.utils.HelperRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Play implements Command {
    private final QuestService questService;
    private final UserService userService;

    public Play(QuestService questService, UserService userService) {
        this.userService = userService;
        this.questService = questService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        Game game = (Game) session.getAttribute("game");
        Long questId;
        try{
            questId = getValidQuestId(request, session);
        }catch (Exception e){
            HelperRequest.setError(request, e.getMessage());
            log.info(e.getMessage());
            return "redirect:" + Go.HOME;
        }
        if (questId == null && game == null) {
            HelperRequest.setError(request, "Выберите квест");
            log.info("Квест не выбран");
            return "redirect:" + Go.HOME;
        }
        if (questId != null) {
            Quest quest = questService.get(questId);
            if (quest == null) {
                HelperRequest.setError(request, "Квеста с id = " + questId + " не существует");
                log.info("Квеста с id = {} не существует", questId);
                return "redirect:" + Go.HOME;
            }
            if (userId != null) {
                User enteredUser = userService.getUser(userId);
                if (enteredUser == null) {
                    HelperRequest.setError(request, "Игрока с id = " + userId + " не существует");
                    log.info("Игрока с id = {} не существует", userId);
                    return "redirect:" + Go.HOME;
                }
                game = enteredUser.getGameMap().get(quest);
                if (game == null) {
                    game = new Game(quest);
                    enteredUser.getGameMap().put(quest, game);
                }
            } else {
                game = new Game(quest);
            }
        }
        session.setAttribute("game", game);
        Question currentQuestion = game.getCurrentQuestion();
        request.setAttribute("question", currentQuestion);
        Statement statement = game.getStatement();
        request.setAttribute("statement", statement);
        return Go.PLAY;
    }

    @Override
    public String doPost(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Game game = (Game) session.getAttribute("game");

        if (game == null) {
            HelperRequest.setError(request, "Нет сохраненной игры");
            log.info("Нет сохраненной игры");
            return Go.HOME;
        }
        Quest quest = game.getQuest();
        Statement statement = game.getStatement();
        Long userId = (Long) session.getAttribute("userId");
        session.removeAttribute("questId");
        if (statement == Statement.PLAY) {
            String nextQuestionIdStr = request.getParameter("nextQuestionId");
            if (nextQuestionIdStr != null && !nextQuestionIdStr.isEmpty()) {
                try {
                    Long nextQuestionId = Long.parseLong(nextQuestionIdStr);
                    game.setCurrentQuestionId(nextQuestionId);
                } catch (NumberFormatException e) {
                    HelperRequest.setError(request, "Что-то пошло не так");
                    log.error(e.getMessage());
                    return Go.HOME;
                }
            }
            game.updateStatement();
            if (userId != null) {
                User enteredUser = userService.getUser(userId);
                enteredUser.getGameMap().put(quest, game);
                statement = game.getStatement();
                WinLossCounter currentCounter = enteredUser.getStats()
                        .computeIfAbsent(quest, k -> new WinLossCounter(0, 0));
                if (statement == Statement.WIN) {
                    enteredUser.getStats().put(quest, currentCounter.addWin());
                    enteredUser.addWinScore();
                    enteredUser.getGameMap().remove(quest);
                }
                if (statement == Statement.LOOSE) {
                    enteredUser.getStats().put(quest, currentCounter.addLoss());
                    enteredUser.addLossScore();
                    enteredUser.getGameMap().remove(quest);
                }
            }
        } else {
            session.setAttribute("questId", quest.getId());
        }
        return Go.PLAY;
    }

    private Long getValidQuestId(HttpServletRequest request, HttpSession session) {
        String questIdReq = request.getParameter("questId");
        if (questIdReq != null && !questIdReq.isEmpty()) {
            try {
                return Long.parseLong(questIdReq);
            } catch (NumberFormatException e) {
                throw new AppException("Некорректный формат questId: " + questIdReq, e);
            }
        }
        Object sessionAttr = session.getAttribute("questId");
        return (sessionAttr instanceof Long) ? (Long) sessionAttr : null;
    }
}




































