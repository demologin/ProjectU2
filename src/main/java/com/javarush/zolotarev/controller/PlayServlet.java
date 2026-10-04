package com.javarush.zolotarev.controller;

import com.javarush.zolotarev.logic.QuestLogic;
import com.javarush.zolotarev.logic.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet({"/play", "/result", "/restart"})
public class PlayServlet extends HttpServlet {
    private final QuestLogic logic = new QuestLogic();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("/result".equals(req.getServletPath())) {
            showResult(req, resp);
            return;
        }
        showQuestion(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        if ("/restart".equals(req.getServletPath())) {
            restart(req, resp);
            return;
        }
        answer(req, resp);
    }

    private void showQuestion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("step") == null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        int step = (Integer) session.getAttribute("step");
        req.setAttribute("question", logic.question(step));
        req.getRequestDispatcher("/WEB-INF/play.jsp").forward(req, resp);
    }

    private void showResult(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("ending") == null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/result.jsp").forward(req, resp);
    }

    private void answer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("step") == null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        String choice = req.getParameter("choice");
        if (choice == null || choice.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/play");
            return;
        }

        int step = (Integer) session.getAttribute("step");
        Result result = logic.answer(step, choice);
        if (result.isFinished()) {
            session.removeAttribute("step");
            session.removeAttribute("note");
            session.setAttribute("won", result.isWon());
            session.setAttribute("ending", result.getMessage());
            resp.sendRedirect(req.getContextPath() + "/result");
            return;
        }
        session.setAttribute("step", result.getNextStep());
        session.setAttribute("note", result.getMessage());
        resp.sendRedirect(req.getContextPath() + "/play");
    }

    private void restart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("playerName") == null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        Integer games = (Integer) session.getAttribute("gamesPlayed");
        session.setAttribute("gamesPlayed", games == null ? 1 : games + 1);
        session.setAttribute("step", QuestLogic.FIRST_STEP);
        session.removeAttribute("note");
        session.removeAttribute("ending");
        session.removeAttribute("won");
        resp.sendRedirect(req.getContextPath() + "/play");
    }
}
