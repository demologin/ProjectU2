package com.javarush.sharifullina.cmd;

import com.javarush.sharifullina.entity.User;
import com.javarush.sharifullina.service.ImageService;
import com.javarush.sharifullina.service.UserService;
import com.javarush.sharifullina.utils.Go;
import com.javarush.sharifullina.utils.HelperRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserProfile implements Command {

    private final UserService userService;
    private final ImageService imageService;


    public UserProfile(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            HelperRequest.setError(request, "Не указан ID пользователя");
            log.error("Не указан ID пользователя");
            return "redirect:" + Go.LIST_USER;
        }

        try {
            Long userId = Long.parseLong(idParam);
            User user = userService.getUser(userId);

            if (user == null) {
                String errorMessage = "Пользователь c id = " + userId + " не найден";
                HelperRequest.setError(request, errorMessage);
                log.error(errorMessage);
                return "redirect:" + Go.LIST_USER;
            }

            request.setAttribute("currentUser", user);

            String imagePath = imageService.getImage(user.getImage());
            String dynamicPath = imagePath + "?t=" + System.currentTimeMillis();
            request.setAttribute("imagePath", dynamicPath);
            return getView();

        } catch (NumberFormatException e) {
            HelperRequest.setError(request, "Некорректный формат ID");
            log.error("Некорректный формат ID");
            return "redirect:" + Go.LIST_USER;
        }
    }
}
