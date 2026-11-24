package com.javarush.sharifullina.cmd;

import com.javarush.sharifullina.entity.Quest;
import com.javarush.sharifullina.service.QuestService;
import com.javarush.sharifullina.utils.Constant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
public class Home implements Command{
    private QuestService questService;

    @Override
    public String doGet(HttpServletRequest request) {
        Collection<Quest> quests = questService.getAll();
        request.setAttribute(Constant.QUESTS, quests);
        return getView();
    }
}
