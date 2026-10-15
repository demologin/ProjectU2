package com.javarush.khmelov.cmd;

import com.javarush.khmelov.entity.Quest;
import com.javarush.khmelov.service.QuestService;
import com.javarush.khmelov.util.Parameter;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Collection;

public class Quests implements Command{
    private final QuestService questService;

    public Quests(QuestService questService) {
        this.questService = questService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        Collection<Quest> quests = questService.getAll();
        request.setAttribute(Parameter.QUESTS, quests);
        return getView();
    }

}
