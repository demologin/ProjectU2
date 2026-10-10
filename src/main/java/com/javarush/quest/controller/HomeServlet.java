package com.javarush.quest.controller;

import com.javarush.quest.config.annotation.Servlet;
import com.javarush.quest.config.constant.Schema.Jsp;
import com.javarush.quest.config.constant.Schema.Key;
import com.javarush.quest.config.constant.Schema.Url;
import com.javarush.quest.controller.base.BaseServlet;
import com.javarush.quest.controller.base.Response;
import com.javarush.quest.service.QuestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Servlet(Url.ROOT)
@RequiredArgsConstructor
public class HomeServlet extends BaseServlet {
    private final QuestService questService;

    @Override
    protected Response handleGet(HttpServletRequest request) {
        request.setAttribute(Key.QUESTS, questService.getQuests());
        return Response.forward(Jsp.HOME);
    }
}
