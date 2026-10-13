package com.javarush.quest.controller;

import com.javarush.quest.config.annotation.Servlet;
import com.javarush.quest.config.constant.Schema.Key;
import com.javarush.quest.config.constant.Schema.Url;
import com.javarush.quest.controller.base.BaseServlet;
import com.javarush.quest.controller.base.Response;
import com.javarush.quest.entity.User;
import com.javarush.quest.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Servlet(Url.LOGIN)
@RequiredArgsConstructor
public class LoginServlet extends BaseServlet {
    private final UserService userService;

    @Override
    protected Response handlePost(HttpServletRequest request) {
        String login = request.getParameter(Key.LOGIN);
        String password = request.getParameter(Key.PASSWORD);
        User user = userService.getUser(login, password);

        request.getSession().setAttribute(Key.USER, user);
        log.info("User {} with id {} logged in", user.getLogin(), user.getId());
        return Response.redirect(Url.HOME);
    }
}
