package com.javarush.quest.controller;

import com.javarush.quest.config.annotation.Servlet;
import com.javarush.quest.config.constant.Schema.Url;
import com.javarush.quest.controller.base.BaseServlet;
import com.javarush.quest.controller.base.Response;
import jakarta.servlet.http.HttpServletRequest;

@Servlet(Url.LOGOUT)
public class LogoutServlet extends BaseServlet {

    @Override
    protected Response handleGet(HttpServletRequest req) {
        req.getSession().invalidate();
        return Response.redirect(Url.LOGIN);
    }
}
