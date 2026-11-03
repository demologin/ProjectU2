package com.javarush.zimina.cmd;

import com.javarush.zimina.entity.User;
import com.javarush.zimina.service.QuestService;
import com.javarush.zimina.util.Go;
import com.javarush.zimina.util.Key;
import jakarta.servlet.http.HttpServletRequest;

public class CreateQuest implements Command {
    private final QuestService questService;

    public CreateQuest(QuestService questService) {
        this.questService = questService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Key.USER);
        String name = request.getParameter(Key.NAME);
        String description = request.getParameter(Key.DESCRIPTION);
        if (name == null || name.isBlank()) {
            return Go.CREATE_QUEST + "?error=empty";
        }
        Long questId = questService.create(name.trim(), description, user.getId());
        return Go.EDIT_QUEST + "?questId=" + questId;
    }
}