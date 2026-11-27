package com.javarush.levanov.cmd;

import com.javarush.levanov.entity.Role;
import com.javarush.levanov.entity.User;
import com.javarush.levanov.service.UserService;
import com.javarush.levanov.util.Parameter;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;


public class EditUser implements Command {

    private final UserService userService;

    public EditUser(UserService userService) {
        this.userService = userService;
    }


    @Override
    public String doGet(HttpServletRequest req) {
        String stringId = req.getParameter(Parameter.ID);
        if (stringId != null) {
            long id = Long.parseLong(stringId);
            Optional<User> optionalUser = userService.get(id);
            optionalUser.ifPresent(user -> req.setAttribute(Parameter.USER, user));
        }
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest req) {
        User user = User.builder()
                .login(req.getParameter(Parameter.LOGIN))
                .password(req.getParameter(Parameter.PASSWORD))
                .role(Role.valueOf(req.getParameter(Parameter.ROLE)))
                .build();
        if (req.getParameter(Parameter.CREATE) != null) {
            userService.create(user);
        } else if (req.getParameter(Parameter.UPDATE) != null) {
            user.setId(Long.parseLong(req.getParameter(Parameter.ID)));
            userService.update(user);
        }
        return getView() + "?" + Parameter.ID + "=" + user.getId();
    }


}