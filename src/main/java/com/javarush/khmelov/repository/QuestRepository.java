package com.javarush.khmelov.repository;

import com.javarush.khmelov.entity.Quest;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class QuestRepository implements Repository<Quest> {

    public static final AtomicLong id = new AtomicLong(System.currentTimeMillis());
    private final Map<Long, Quest> map = new HashMap<>();

    public QuestRepository() {
        map.put(1L, new Quest(1L, "Похищение пришельцами", "Внеземной разум вступил с тобой в контакт и тебе нужно не ударить в грязь лицом"));
    }

    @Override
    public Collection<Quest> getAll() {
        return map.values();
    }

    @Override
    public Optional<Quest> get(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public void create(Quest entity) {

    }

    @Override
    public void update(Quest entity) {

    }

    @Override
    public void delete(Quest entity) {

    }
}
