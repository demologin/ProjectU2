package com.javarush.levanov.cmd;

import com.javarush.levanov.entity.User;
import com.javarush.levanov.service.UserService;
import com.javarush.levanov.util.Go;
import com.javarush.levanov.util.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class Login implements Command {

    private final UserService userService;

    public Login(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doPost(HttpServletRequest req) {
        String login = req.getParameter(Parameter.LOGIN);
        String password = req.getParameter(Parameter.PASSWORD);
        Optional<User> user = userService.get(login, password);
        if (user.isPresent()) {
            HttpSession session = req.getSession();
            session.setAttribute(Parameter.USER, user.get());
            return Go.START_PAGE;
        }
        return Go.LOGIN;
    }
}
