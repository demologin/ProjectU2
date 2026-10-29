package com.javarush.rukavanov.repository;

import com.javarush.rukavanov.entity.Quest;
import com.javarush.rukavanov.util.QuestParser;

import java.io.IOException;
import java.util.*;

public class QuestRepository {
    private final QuestParser questParser;

    private final Map<Long, Quest> quests = new HashMap<>();

    public QuestRepository(QuestParser questParser) throws IOException {
        this.questParser = questParser;
        loadQuests();
    }


    public void save(Quest quest) {
        quests.put(quest.getId(), quest);
    }

    public Optional<Quest> findById(Long id) {
        return Optional.ofNullable(quests.get(id));
    }

    public List<Quest> findAll() {
        return new ArrayList<>(quests.values());
    }

    private void loadQuests() throws IOException {
        List<Quest> questList = questParser.parseAllQuests();
        for (Quest quest : questList) {
            save(quest);
        }

    }


}
