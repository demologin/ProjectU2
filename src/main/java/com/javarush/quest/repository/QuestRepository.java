package com.javarush.quest.repository;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.Quest;
import com.javarush.quest.repository.base.BaseRepository;

import java.util.UUID;

@Component
public class QuestRepository extends BaseRepository<Quest, String> {

    @Override
    protected String generateId() {
        return UUID.randomUUID().toString();
    }
}
