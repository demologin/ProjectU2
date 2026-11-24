package com.javarush.sharifullina.service;

import com.javarush.sharifullina.entity.Quest;
import com.javarush.sharifullina.repository.QuestRepository;

import java.util.Collection;

public class QuestService {
    QuestRepository questRepository;

    public QuestService(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    public void create(Quest quest) {
        questRepository.create(quest);
    }

    public void update(Quest quest) {
        questRepository.update(quest);
    }

    public void delete(Quest quest) {
        questRepository.delete(quest);
    }

    public Collection<Quest> getAll() {
        return questRepository.getAll();
    }

    public Quest get(long id) {
        return questRepository.get(id).orElse(null);
    }
}
