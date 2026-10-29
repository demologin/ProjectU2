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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserService userService;

    public RegisterServlet() throws IOException {
        this.userService = new UserService(new UserRepository());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        boolean isRegistered = userService.register(user);

        if (isRegistered) {
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/home-quest");
        } else {
            req.setAttribute("error", "Username is already in use");
            req.getRequestDispatcher("/WEB-INF/views/create_user.jsp").forward(req, resp);
        }

    }
}
