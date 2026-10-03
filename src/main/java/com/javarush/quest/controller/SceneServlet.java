package com.javarush.quest.controller;

import com.javarush.quest.config.annotation.Servlet;
import com.javarush.quest.entity.Scene;
import com.javarush.quest.service.SceneService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@Servlet("/scenes")
@RequiredArgsConstructor
public class SceneServlet extends HttpServlet {
    private final SceneService sceneService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException {
        String sceneId = req.getParameter("id");
        Scene scene = sceneService.getSceneById(sceneId);
        req.setAttribute("scene", scene);
        req.getRequestDispatcher("/WEB-INF/scene.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException {
        String sceneId = req.getParameter("id");
        String playerName = req.getParameter("playerName");
        req.getSession().setAttribute("playerName", playerName);
        resp.sendRedirect(req.getContextPath() + "/scenes?id=" + sceneId);
    }
}
