package com.javarush.khmelov.cmd;

import com.javarush.khmelov.entity.Role;
import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.service.UserService;
import com.javarush.khmelov.util.Parameter;
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