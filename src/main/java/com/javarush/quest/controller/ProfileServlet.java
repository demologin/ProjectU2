package com.javarush.quest.controller;

import com.javarush.quest.config.annotation.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@Servlet("/users")
@RequiredArgsConstructor
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
    }
}
