package com.javarush.sharifullina.cmd;

import com.javarush.sharifullina.entity.*;
import com.javarush.sharifullina.exception.AppException;
import com.javarush.sharifullina.service.ImageService;
import com.javarush.sharifullina.service.UserService;
import com.javarush.sharifullina.utils.Go;
import com.javarush.sharifullina.utils.HelperRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Slf4j
@MultipartConfig
public class PrivateProfileEdit implements Command {

    private final UserService userService;
    private final ImageService imageService;

    public PrivateProfileEdit(UserService userService,  ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        Long id = getId(request);
        User user = userService.getUser(id);
        if (user == null) {
            String errorMessage = "Пользователь с id = " + id + " не найден";
            HelperRequest.setError(request, errorMessage);
            log.error(errorMessage);
            return "redirect:" + Go.HOME;
        }
        request.setAttribute("enteredUser", user);

        String imagePath = imageService.getImage(user.getImage());

        String dynamicPath = imagePath + "?t=" + System.currentTimeMillis();
        request.setAttribute("imagePath", dynamicPath);

        return getView();
    }

    @Override
    public String doPost(HttpServletRequest request)  {
        try {
            String bdString = getParameter(request, "birthday");
            LocalDate birthday = (bdString == null) ? null : LocalDate.parse(bdString);
            Role role = Role.valueOf(getParameter(request, "role"));
            Long id = getId(request);
            User user = userService.getUser(id);

            user.setRole(role);
            user.setBirthday(birthday);
            user.setName(getParameter(request, "name"));
            user.setLogin(getParameter(request, "login"));
            user.setSurname(getParameter(request, "surname"));

            userService.updateUser(user);

            imageService.uploadImage(request, user.getImage());
            request.getSession().setAttribute("role", user.getRole());
        }catch (AppException e){
            HelperRequest.setError(request, e.getMessage());
            log.error(e.getMessage());
            return getView();
        }
        return getView();
    }

    private Long getId(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute("userId");
    }

    private String getParameter(HttpServletRequest request, String name) {
        Part loginPart;
        String parameter = null;
        try {
            loginPart = request.getPart(name);
        } catch (IOException | ServletException e) {
            throw new AppException("Поля '" + name +"' не существует",e);
        }
        if (loginPart != null && loginPart.getSize() > 0) {
            try (InputStream is = loginPart.getInputStream()) {
                String extracted = new String(is.readAllBytes(), StandardCharsets.UTF_8).trim();
                parameter = (!extracted.isEmpty()) ? extracted : null;
            } catch (IOException e) {
                throw new AppException(e);
            }
        }
        return parameter;
    }
}
