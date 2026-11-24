package com.javarush.sharifullina.cmd;

import com.javarush.sharifullina.entity.User;
import com.javarush.sharifullina.service.UserService;
import com.javarush.sharifullina.utils.Constant;
import com.javarush.sharifullina.utils.Go;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Collection;

public class Statistic implements Command{
    private final UserService userService;

    public Statistic(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        Collection<User> topUsers = userService.getSortedLimit(Constant.LIMIT);
        request.setAttribute("topUsers", topUsers);
        return Go.STATISTIC;
    }
}
