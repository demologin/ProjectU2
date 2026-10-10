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

    private static final String SAMPLE_USERS = "users.json";
    private static final String SAMPLE_QUESTS = "quests.yaml";

    public void loadData() {
        List<User> users = resourceLoader.loadList(SAMPLE_USERS, User.class);
        users.forEach(userService::createUser);

        List<Quest> quests = resourceLoader.loadList(SAMPLE_QUESTS, Quest.class);
        quests.forEach(questService::createQuest);
    }
}
