package com.javarush.quest.config;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.Quest;
import com.javarush.quest.entity.User;
import com.javarush.quest.service.QuestService;
import com.javarush.quest.service.UserService;
import com.javarush.quest.util.ResourceLoader;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final UserService userService;
    private final QuestService questService;
    private final ResourceLoader resourceLoader;

    private static final String USERS = "users.json";
    private static final String QUESTS = "quests.yaml";

    public void initialize() {
        List<User> users = resourceLoader.loadList(USERS, User.class);
        users.forEach(userService::createUser);

        List<Quest> quests = resourceLoader.loadList(QUESTS, Quest.class);
        quests.forEach(questService::createQuest);
    }
}
