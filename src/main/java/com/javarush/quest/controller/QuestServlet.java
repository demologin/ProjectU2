package com.javarush.quest.controller;

import com.javarush.quest.config.annotation.Servlet;
import com.javarush.quest.entity.Quest;
import com.javarush.quest.service.QuestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@Servlet("/quests")
@RequiredArgsConstructor
public class QuestServlet extends HttpServlet {
    private final QuestService questService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException {
        String questId = req.getParameter("id");
        if (questId != null) {
            Quest quest = questService.findById(questId);
            req.setAttribute("quest", quest);
            req.getRequestDispatcher("/WEB-INF/quest.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}
