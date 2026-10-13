package com.javarush.quest.controller;

import com.javarush.quest.config.annotation.Servlet;
import com.javarush.quest.config.constant.Schema.Jsp;
import com.javarush.quest.config.constant.Schema.Key;
import com.javarush.quest.config.constant.Schema.Url;
import com.javarush.quest.controller.base.BaseServlet;
import com.javarush.quest.controller.base.Response;
import com.javarush.quest.entity.User;
import com.javarush.quest.entity.UserStats;
import com.javarush.quest.service.StatsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Servlet(urlPatterns = Url.USERS, defaultJsp = Jsp.PROFILE)
@RequiredArgsConstructor
public class ProfileServlet extends BaseServlet {
    private final StatsService statsService;

    @Override
    protected Response handleGet(HttpServletRequest request) {
        Long userId = ((User) request.getAttribute(Key.USER)).getId();
        UserStats userStats = statsService.collectStats(userId);
        request.setAttribute(Key.USER_STATS, userStats);
        return Response.JSP;
    }
}
