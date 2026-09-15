package com.javarush.khmelov.app.cmd;

import com.javarush.khmelov.app.entity.Role;
import com.javarush.khmelov.app.entity.User;
import com.javarush.khmelov.app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class EditUser implements Command {

    private final UserService userService;

    public EditUser(UserService userService) {
        this.userService = userService;
    }


    @Override
    public String doGet(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (id != null && !id.isEmpty()) {
            long userId = Long.parseLong(id);
            Optional<User> optUser = userService.get(userId);
            User user = optUser.orElseThrow();
            req.setAttribute("user", user);
        }
        return getView();
    }


    @Override
    public String doPost(HttpServletRequest req) {
        String strId = req.getParameter("id");
        Long id = (strId == null || strId.isEmpty())
                ? null
                : Long.parseLong(strId);
        User user = User.builder()
                .id(id)
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .role(Role.valueOf(req.getParameter("role")))
                .build();
        if (req.getParameter("create") != null) {
            userService.create(user);
        } else if (req.getParameter("update") != null) {
            userService.update(user);
        } else {
            throw new RuntimeException("incorrect form data");
        }
        return getView() + "?id=" + user.getId();
    }
}
