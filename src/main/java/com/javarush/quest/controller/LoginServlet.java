package com.javarush.quest.controller;

import com.javarush.quest.config.annotation.Servlet;
import com.javarush.quest.entity.User;
import com.javarush.quest.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@Servlet("/login")
@RequiredArgsConstructor
public class LoginServlet extends HttpServlet {
    private final UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = userService.getUser(login, password);
        req.getSession().setAttribute("user", user);
        resp.sendRedirect(req.getContextPath() + "/"); //todo go home?
    }
}
