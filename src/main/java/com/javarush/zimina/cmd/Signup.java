package com.javarush.zimina.cmd;

import com.javarush.zimina.service.UserService;
import com.javarush.zimina.util.Go;
import com.javarush.zimina.util.Key;
import jakarta.servlet.http.HttpServletRequest;

public class Signup implements Command{
    private final UserService userService;

    public Signup(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doPost(HttpServletRequest request) {
        String login = request.getParameter(Key.LOGIN);
        String password = request.getParameter(Key.PASSWORD);
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            return Go.SIGNUP + "?error=empty";
        }
        if(userService.register(login, password)){
            return Go.LOGIN;
        }
        return Go.SIGNUP + "?error=taken";
    }
}
