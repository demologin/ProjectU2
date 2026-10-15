package com.javarush.khmelov.cmd;

import com.javarush.khmelov.entity.State;
import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.service.StatisticService;
import com.javarush.khmelov.util.Parameter;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class Statistics implements Command {
    private final StatisticService statisticService;

    public Statistics(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        Map<User, Map<State, Integer>> userStat = statisticService.getCommonStat();
        req.setAttribute(Parameter.USER_STAT, userStat);
        Map<User, Integer> totalStat = statisticService.getUserTotalStat();
        req.setAttribute(Parameter.TOTAL_STAT, totalStat);
        return getView();
    }
}
