package com.javarush.rukavanov.controller;


import com.javarush.rukavanov.entity.User;
import com.javarush.rukavanov.repository.UserRepository;
import com.javarush.rukavanov.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/profile-page")
public class ProfilePageServlet extends HttpServlet {
    private UserService userService;

    public ProfilePageServlet() throws IOException {
        this.userService = new UserService(new UserRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getSession(false) == null || req.getSession(false).getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login-page");
            return;

        }
        req.getRequestDispatcher("/WEB-INF/views/edit_user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println(user.getId());

        user.setUsername(username);
        if (password != null && !password.isBlank()) {
            user.setPassword(password);
        }

        userService.update(user);
        resp.sendRedirect(req.getContextPath() + "/home-quest");

    }

}
