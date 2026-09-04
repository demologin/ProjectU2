package com.javarush.khmelov.lesson13.controller;

import com.javarush.khmelov.lesson13.entity.User;
import com.javarush.khmelov.lesson13.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/edit-user")
public class EditUserServlet extends HttpServlet {

    private final UserService userService = UserService.USER_SERVICE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        long userId = Long.parseLong(id);
        Optional<User> optUser = userService.get(userId);
        User user = optUser.orElseThrow();
        req.setAttribute("user", user);
        req.getRequestDispatcher("WEB-INF/edit-user.jsp")
                .forward(req, resp);
    }
}
