package com.javarush.khmelov.lesson14.controller;

import com.javarush.khmelov.lesson14.config.Winter;
import com.javarush.khmelov.lesson14.entity.User;
import com.javarush.khmelov.lesson14.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/list-user")
public class ListUserServlet extends HttpServlet {

    private final UserService userService= Winter.getBean(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<User> users = userService.getAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("WEB-INF/list-user.jsp")
                .forward(req, resp);
    }
}
