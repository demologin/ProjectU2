package com.javarush.khmelov.app.cmd;

import com.javarush.khmelov.app.entity.User;
import com.javarush.khmelov.app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import java.util.Collection;


@AllArgsConstructor
public class ListUser implements Command {

    private final UserService userService;

    @Override
    public String doGet(HttpServletRequest req) {
        Collection<User> users = userService.getAll();
        req.setAttribute("users", users);
        return getView();
    }
}
