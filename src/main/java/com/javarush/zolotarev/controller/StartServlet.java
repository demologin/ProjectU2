package com.javarush.zolotarev.controller;

import com.javarush.zolotarev.logic.QuestLogic;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/start")
public class StartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        session.setAttribute("playerName", req.getParameter("name"));

        Integer games = (Integer) session.getAttribute("gamesPlayed");
        session.setAttribute("gamesPlayed", games == null ? 1 : games + 1);

        session.setAttribute("step", QuestLogic.FIRST_STEP);

        session.removeAttribute("note");
        session.removeAttribute("ending");
        session.removeAttribute("won");

        resp.sendRedirect(req.getContextPath() + "/play");
    }
}