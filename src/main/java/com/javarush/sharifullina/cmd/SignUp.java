package com.javarush.sharifullina.cmd;

import com.javarush.sharifullina.entity.Role;
import com.javarush.sharifullina.entity.User;
import com.javarush.sharifullina.service.UserService;
import com.javarush.sharifullina.utils.Constant;
import com.javarush.sharifullina.utils.Go;
import com.javarush.sharifullina.utils.HelperRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SignUp implements Command {
    private final UserService userService;

    public SignUp(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doPost(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = request.getParameter(Constant.LOGIN);
        String passwordFirst = request.getParameter(Constant.PASSWORD_FIRST);
        String passwordSecond = request.getParameter(Constant.PASSWORD_SECOND);

        if (passwordFirst.isEmpty() || passwordSecond.isEmpty()||login.isEmpty()) {
            HelperRequest.setError(request, "Заполните поля");
            log.info("Отправлены пустые поля");
            return Go.SIGN_UP;
        }

        if (userService.isRepeatLogin(login)) {
            HelperRequest.setError(request, "Логин уже существует");
            log.info("Введен существующий логин");
            return Go.SIGN_UP;
        }

        if (!passwordFirst.equals(passwordSecond)) {
            HelperRequest.setError(request, "Введены разные пароли");
            log.info("Введены разные пароли");
            return Go.SIGN_UP;
        }

        User user = User.builder()
                .login(login)
                .password(passwordFirst)
                .role(Role.USER)
                .build();

        Long id = userService.createUser(user);
        session.setAttribute("userId", id);
        session.setAttribute("role", Role.USER);

        return Go.PRIVATE_PROFILE_EDIT;
    }
}



















