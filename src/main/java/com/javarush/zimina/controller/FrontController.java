package com.javarush.zimina.controller;


import com.javarush.zimina.cmd.Command;
import com.javarush.zimina.config.Container;
import com.javarush.zimina.loader.QuestLoader;
import com.javarush.zimina.util.Go;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet({Go.INDEX, Go.HOME, Go.SIGNUP, Go.LOGIN, Go.LOGOUT, Go.PROFILE, Go.LIST_QUEST, Go.PLAY_GAME})
public class FrontController extends HttpServlet {

    private final HttpResolver httpResolver = Container.find(HttpResolver.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Container.find(QuestLoader.class).load("quests/grimoire.json");
        Container.find(QuestLoader.class).load("quests/bookseller.json");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = httpResolver.resolve(req);
        String view = command.doGet(req);
        String jsp = "/WEB-INF/" + view + ".jsp";
        req.getRequestDispatcher(jsp).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = httpResolver.resolve(req);
        String redirect = command.doPost(req);
        resp.sendRedirect(redirect);
    }
}
