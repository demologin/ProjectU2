package com.javarush.zimina.cmd;

import com.javarush.zimina.util.Go;
import jakarta.servlet.http.HttpServletRequest;

public class Logout implements Command {

    @Override
    public String doPost(HttpServletRequest request) {
        request.getSession().invalidate();
        return Go.HOME;
    }
}
