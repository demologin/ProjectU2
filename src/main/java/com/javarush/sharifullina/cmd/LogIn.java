package com.javarush.sharifullina.cmd;

import com.javarush.sharifullina.entity.User;
import com.javarush.sharifullina.service.UserService;
import com.javarush.sharifullina.utils.Constant;
import com.javarush.sharifullina.utils.Go;
import com.javarush.sharifullina.utils.HelperRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogIn implements Command {
    private final UserService userService;

    public LogIn(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doPost(HttpServletRequest request) {
        User user = userService.getUser(request.getParameter(Constant.LOGIN),
                request.getParameter(Constant.PASSWORD));
        HttpSession session = request.getSession();

        if (user == null) {
            HelperRequest.setError(request, "Неправильный логин или пароль");
            log.info("Неправильный логин или пароль");
            return getView();
        } else {
            Long id = user.getId();
            session.setAttribute(Constant.USER_ID, id);
            session.setAttribute(Constant.ROLE, user.getRole());
        }
        return Go.HOME;
    }
}














