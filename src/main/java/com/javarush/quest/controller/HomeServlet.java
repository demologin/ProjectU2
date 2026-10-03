package com.javarush.quest.controller;

import com.javarush.quest.config.annotation.Servlet;
import com.javarush.quest.entity.Quest;
import com.javarush.quest.repository.QuestRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@Servlet("")
@RequiredArgsConstructor
public class HomeServlet extends HttpServlet {
    private final QuestRepository questRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException {
        List<Quest> quests = questRepository.findAll();
        req.setAttribute("quests", quests);
        req.getRequestDispatcher("/WEB-INF/home.jsp").forward(req, resp);
    }
}
