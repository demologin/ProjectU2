package com.javarush.rukavanov.controller;

import com.javarush.rukavanov.service.QuestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/home-quest")
public class HomeServlet extends HttpServlet {
    private QuestService questService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("quests", questService.findAll());
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);

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
