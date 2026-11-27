package com.javarush.levanov.cmd;

import com.javarush.levanov.entity.User;
import com.javarush.levanov.service.UserService;
import com.javarush.levanov.util.Parameter;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Collection;

public class ListUser implements Command {

    private final UserService userService;

    public ListUser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        Collection<User> users = userService.getAll();
        request.setAttribute(Parameter.USERS, users);
        return getView();
    }

}