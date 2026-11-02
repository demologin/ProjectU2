package com.javarush.zimina.cmd;

import com.javarush.zimina.entity.Quest;
import com.javarush.zimina.entity.Stats;
import com.javarush.zimina.service.QuestService;
import com.javarush.zimina.service.StatisticsService;
import com.javarush.zimina.util.Key;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListQuest implements Command {
    private final QuestService questService;
    private final StatisticsService statisticsService;

    public ListQuest(QuestService questService, StatisticsService statisticsService) {
        this.questService = questService;
        this.statisticsService = statisticsService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        List<Quest> quests = questService.getAll();
        Map<Long, Stats> stats = new HashMap<>();
        for (Quest quest : quests) {
            stats.put(quest.getId(), statisticsService.forQuest(quest.getId()));
        }
        request.setAttribute(Key.QUESTS, quests);
        request.setAttribute(Key.STATS, stats);
        return getView();
    }
}