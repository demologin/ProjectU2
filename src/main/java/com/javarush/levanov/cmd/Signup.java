package com.javarush.levanov.cmd;

import com.javarush.levanov.entity.Role;
import com.javarush.levanov.entity.User;
import com.javarush.levanov.service.UserService;
import com.javarush.levanov.util.Go;
import com.javarush.levanov.util.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class Signup implements Command {

    private final UserService userService;

    public Signup(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doPost(HttpServletRequest req) {
        User user = User.builder()
                .login(req.getParameter(Parameter.LOGIN))
                .password(req.getParameter(Parameter.PASSWORD))
                .role(Role.USER)
                .build();
        userService.create(user);
        HttpSession session = req.getSession();
        session.setAttribute(Parameter.USER, user);
        return Go.START_PAGE;
    }
}
