package com.javarush.sharifullina.cmd;

import com.javarush.sharifullina.service.UserService;
import com.javarush.sharifullina.utils.Constant;
import jakarta.servlet.http.HttpServletRequest;

public class ListUser implements Command {
    private final UserService userService;

    public ListUser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        request.setAttribute(Constant.USERS, userService.getAll());
        return getView();
    }
}
