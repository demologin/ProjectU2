package com.javarush.quest.controller;

import com.javarush.quest.config.annotation.Servlet;
import com.javarush.quest.service.QuestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@Servlet("")
@RequiredArgsConstructor
public class HomeServlet extends HttpServlet {
    private final QuestService questService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException {
        req.setAttribute("quests", questService.findAll());
        req.getRequestDispatcher("/WEB-INF/home.jsp").forward(req, resp);
    }
}
