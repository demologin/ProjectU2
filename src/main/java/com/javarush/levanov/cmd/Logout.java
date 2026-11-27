package com.javarush.levanov.cmd;

import com.javarush.levanov.util.Go;
import jakarta.servlet.http.HttpServletRequest;

public class Logout implements Command {

    @Override
    public String doPost(HttpServletRequest req) {
        req.getSession().invalidate();
        return Go.START_PAGE;
    }
}
