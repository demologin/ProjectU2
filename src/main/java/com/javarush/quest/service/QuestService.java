package com.javarush.quest.service;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.Quest;
import com.javarush.quest.repository.QuestRepository;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class QuestService {
    private final QuestRepository questRepository;

    public Quest findById(String id) {
        return questRepository.findById(id).orElseThrow();
    }

    public Collection<Quest> findAll() {
        return questRepository.findAll();
    }
}
