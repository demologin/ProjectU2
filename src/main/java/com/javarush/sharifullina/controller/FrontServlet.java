package com.javarush.sharifullina.controller;

import com.javarush.sharifullina.cmd.Command;
import com.javarush.sharifullina.config.Winter;
import com.javarush.sharifullina.entity.Role;
import com.javarush.sharifullina.init.InitQuestCreator;
import com.javarush.sharifullina.init.InitUsersCreator;
import com.javarush.sharifullina.utils.Go;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@MultipartConfig
@WebServlet({Go.EMPTY, Go.CONTACTS, Go.CREATE_QUEST, Go.EXIT, Go.HOME, Go.LIST_USER, Go.LOG_IN,
        Go.PRIVATE_PROFILE_EDIT, Go.SIGN_UP, Go.STATISTIC, Go.USER_PROFILE, Go.PLAY})
public class FrontServlet extends HttpServlet {

    private final HttpResolver httpResolver = Winter.getBean(HttpResolver.class);

    @Override
    public void init(ServletConfig config) {
        InitUsersCreator usersCreator = Winter.getBean(InitUsersCreator.class);
        usersCreator.createUsers();

        InitQuestCreator questCreator = Winter.getBean(InitQuestCreator.class);
        questCreator.createQuests();

        ServletContext servletContext = config.getServletContext();
        servletContext.setAttribute("roles", Role.values());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = httpResolver.resolve(request);
        String view = command.doGet(request);
        if (view.startsWith("redirect:")) {
            response.sendRedirect(view.substring("redirect:".length()));
        } else {
            String jsp = getJsp(view);
            request.getRequestDispatcher(jsp).forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Command command = httpResolver.resolve(request);
        String redirect = command.doPost(request);
        response.sendRedirect(redirect);
    }

    private String getJsp(String view) {
        return "WEB-INF/" + view + ".jsp";
    }
}
