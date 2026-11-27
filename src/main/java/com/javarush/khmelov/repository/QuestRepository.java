package com.javarush.khmelov.repository;

import com.javarush.khmelov.entity.Quest;
import com.javarush.lesson09.SessionCreator;

import java.util.stream.Stream;

public class QuestRepository extends BaseRepository<Quest> {

    public QuestRepository(SessionCreator sessionCreator) {
        super(Quest.class, sessionCreator);
    }
}
