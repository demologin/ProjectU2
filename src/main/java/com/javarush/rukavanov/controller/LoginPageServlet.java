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
import java.util.List;

@WebServlet("/login-page")
public class LoginPageServlet extends HttpServlet {
    private final UserService userService;

    public LoginPageServlet() throws IOException {
        this.userService = new UserService(new UserRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<User> users = userService.findAll();
        User userDefault = users.get(0);

        req.setAttribute("defaultUsername", userDefault.getUsername());
        req.setAttribute("defaultPassword", userDefault.getPassword());
        req.getRequestDispatcher("/WEB-INF/views/login.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean isAuthenticated = userService.authenticate(username, password);
        if (isAuthenticated) {
            User user = userService.findByUsername(username).orElseThrow();
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/home-quest");
        } else {
            req.setAttribute("error", "Неверное имя пользователя или пароль");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp")
                    .forward(req, resp);

        }

    }

}
