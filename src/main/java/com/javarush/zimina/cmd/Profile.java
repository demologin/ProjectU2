package com.javarush.zimina.cmd;

import com.javarush.zimina.entity.User;
import com.javarush.zimina.service.StatisticsService;
import com.javarush.zimina.util.Key;
import jakarta.servlet.http.HttpServletRequest;

public class Profile implements Command {
    private final StatisticsService statisticsService;

    public Profile(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Key.USER);
        if (user != null) {
            request.setAttribute(Key.STATS, statisticsService.forUser(user.getId()));
        }
        return getView();
    }
}