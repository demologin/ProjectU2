package com.javarush.quest.controller;

import com.javarush.quest.config.annotation.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Servlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
