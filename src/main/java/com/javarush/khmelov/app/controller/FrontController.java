package com.javarush.khmelov.app.controller;

import com.javarush.khmelov.app.cmd.Command;
import com.javarush.khmelov.app.config.Winter;
import com.javarush.khmelov.app.entity.Role;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = {"", "/edit-user", "/list-user"}, loadOnStartup = 1)
public class FrontController extends HttpServlet {

    private final HttpResolver httpResolver = Winter.getBean(HttpResolver.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext appScope = config.getServletContext();
        appScope.setAttribute("roles", Role.values());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = httpResolver.resolve(req);
        String view = command.doGet(req);
        String jsp = getJsp(view);
        req.getRequestDispatcher(jsp).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = httpResolver.resolve(req);
        String redirect = command.doPost(req);
        resp.sendRedirect(redirect);
    }

    private String getJsp(String view) {
        return "WEB-INF/" + view + ".jsp";
    }
}
