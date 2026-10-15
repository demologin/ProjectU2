package com.javarush.khmelov.service;

import com.javarush.khmelov.entity.Quest;
import com.javarush.khmelov.repository.QuestRepository;

import java.util.Collection;
import java.util.Optional;

public class QuestService {
    private final QuestRepository questRepository;

    public QuestService(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    public Optional<Quest> get(long id) {
        return questRepository.get(id);
    }

    public Collection<Quest> getAll() {
        return questRepository.getAll();
    }
}
