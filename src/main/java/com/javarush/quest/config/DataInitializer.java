package com.javarush.quest.config;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.Quest;
import com.javarush.quest.entity.User;
import com.javarush.quest.repository.QuestRepository;
import com.javarush.quest.repository.SceneRepository;
import com.javarush.quest.repository.UserRepository;
import com.javarush.quest.util.ResourceLoader;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final ResourceLoader resourceLoader;
    private final QuestRepository questRepository;
    private final SceneRepository sceneRepository;
    private final UserRepository userRepository;

    public void initialize() {
        List<Quest> quests = resourceLoader.loadList("quests.yaml", Quest.class); //todo string constant
        for (Quest quest : quests) {
            quest.getScenes().forEach(sceneRepository::save);
            questRepository.save(quest);
        }

        List<User> users = resourceLoader.loadList("users.json", User.class);
        users.forEach(userRepository::save);
    }
}
