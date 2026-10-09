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

@Servlet(Url.SIGNUP)
@RequiredArgsConstructor
public class SignupServlet extends BaseServlet {
    private final UserService userService;

    @Override
    protected Response handlePost(HttpServletRequest request) {
        String login = request.getParameter(Key.LOGIN);
        String password = request.getParameter(Key.PASSWORD);
        User user = userService.createUser(login, password);

        request.getSession().setAttribute(Key.USER, user);
        return Response
                .redirect(Url.USERS)
                .withParam(Key.ID, user.getId());
    }
}
