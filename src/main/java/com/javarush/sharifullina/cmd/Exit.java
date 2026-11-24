package com.javarush.sharifullina.cmd;

import jakarta.servlet.http.HttpServletRequest;

public class Exit implements Command {
    @Override
    public String doPost(HttpServletRequest request) {
        request.getSession().removeAttribute("userId");
        return "home";
    }
}
