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
    protected Response handlePost(HttpServletRequest req) {
        User user = User.builder()
                .login(req.getParameter(Key.LOGIN))
                .password(req.getParameter(Key.PASSWORD))
                .build();
        userService.createUser(user);
        req.getSession().setAttribute(Key.USER, user);
        return Response.redirect(Url.USERS).withParam(Key.ID, user.getId());
    }
}
