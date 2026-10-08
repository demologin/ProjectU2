package com.javarush.quest.controller;

import com.javarush.quest.config.annotation.Servlet;
import com.javarush.quest.config.constant.Schema.Key;
import com.javarush.quest.config.constant.Schema.Url;
import com.javarush.quest.controller.base.BaseServlet;
import com.javarush.quest.controller.base.Response;
import com.javarush.quest.service.QuestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Servlet(Url.QUEST)
@RequiredArgsConstructor
public class QuestServlet extends BaseServlet {
    private final QuestService questService;

    @Override
    protected Response handleGet(HttpServletRequest req) {
        String questId = req.getParameter(Key.ID);
        req.setAttribute(Key.QUEST, questService.getQuest(questId));
        return Response.DEFAULT; //todo if questId == null
    }
}
