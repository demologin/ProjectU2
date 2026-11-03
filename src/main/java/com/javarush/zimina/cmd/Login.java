package com.javarush.zimina.cmd;

import com.javarush.zimina.entity.User;
import com.javarush.zimina.service.UserService;
import com.javarush.zimina.util.Go;
import com.javarush.zimina.util.Key;
import jakarta.servlet.http.HttpServletRequest;

public class Login implements Command {
    private final UserService userService;

    public Login(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doPost(HttpServletRequest request) {
        String login = request.getParameter(Key.LOGIN);
        String password = request.getParameter(Key.PASSWORD);
        User user = userService.login(login, password);
        if (user == null) {
            return Go.LOGIN + "?error=wrong";
        }
        request.getSession().setAttribute(Key.USER, user);
        return Go.HOME;
    }
}
