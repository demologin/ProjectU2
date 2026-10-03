package com.javarush.quest.repository;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.Quest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class QuestRepository {
    private final Map<String, Quest> quests = new ConcurrentHashMap<>();

    public void save(Quest quest) {
        quests.put(quest.getId(), quest);
    }

    public Optional<Quest> findById(String id) {
        return Optional.ofNullable(quests.get(id));
    }

    public List<Quest> findAll() {
        return quests.values().stream().toList();
    }
}
