package com.javarush.rukavanov.controller;

import com.javarush.rukavanov.entity.Quest;
import com.javarush.rukavanov.entity.QuestResult;
import com.javarush.rukavanov.entity.QuestStatus;
import com.javarush.rukavanov.service.QuestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/quest-page")
public class QuestPageServlet extends HttpServlet {
    private QuestService questService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String questIdParameter = req.getParameter("questId");
        Long questId = Long.parseLong(questIdParameter);
        Quest quest = questService.findById(questId)
                .orElseThrow(() -> new ServletException("quest not found"));
        req.setAttribute("quest", quest);
        req.setAttribute("currentQuestion", quest.getQuestions().get(0));

        req.getRequestDispatcher("/WEB-INF/views/quest.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String questIdParameter = req.getParameter("questId");
        String answerIdParameter = req.getParameter("answerId");
        Long answerId = Long.parseLong(answerIdParameter);
        Long questId = Long.parseLong(questIdParameter);

        QuestResult result = questService.findNextQuestion(questId, answerId);
        if (result.getQuestStatus() == QuestStatus.IN_PROGRESS) {
            req.setAttribute("quest", questService.findById(questId).orElseThrow());
            req.setAttribute("currentQuestion", result.getNextQuestion());
            req.getRequestDispatcher("/WEB-INF/views/quest.jsp").forward(req, resp);
        }
        if (result.getQuestStatus() == QuestStatus.VICTORY
                || result.getQuestStatus() == QuestStatus.DEFEAT) {

            req.setAttribute("questStatus", result.getQuestStatus());
            req.setAttribute("quest",
                    questService.findById(questId).orElseThrow());

            req.getRequestDispatcher("/WEB-INF/views/quest_result.jsp")
                    .forward(req, resp);
        }

    }

    @Override
    public void init() throws ServletException {
        try {
            questService = new QuestService();
        } catch (IOException e) {
            throw new ServletException("Не удалось загрузить квесты", e);
        }
    }
}
