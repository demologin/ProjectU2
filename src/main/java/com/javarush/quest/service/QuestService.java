package com.javarush.quest.service;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.Quest;
import com.javarush.quest.repository.QuestRepository;
import com.javarush.quest.repository.SceneRepository;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class QuestService {
    private final QuestRepository questRepository;
    private final SceneRepository sceneRepository;

    public Quest getQuest(String questId) {
        return questRepository.findById(questId).orElseThrow();
    }

    public Collection<Quest> getQuests() {
        return questRepository.findAll();
    }

    //todo generate ids
    public void createQuest(Quest quest) {
        questRepository.save(quest);
        quest.getScenes().forEach(sceneRepository::save);
    }
}
