package com.javarush.khmelov.repository;

import com.javarush.khmelov.entity.Answer;
import com.javarush.khmelov.entity.Quest;
import com.javarush.khmelov.entity.State;
import com.javarush.khmelov.entity.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class AnswerRepository implements Repository<Answer> {

    public static final AtomicLong id = new AtomicLong(System.currentTimeMillis());
    private final Map<Long, Answer> map = new HashMap<>();

    public AnswerRepository() {
        map.put(1L, new Answer(1L, 1L, "Принять вызов", State.PLAY));
        map.put(2L, new Answer(2L, 1L, "Отклонить вызов", State.LOSS));
        map.put(3L, new Answer(3L, 2L, "Подняться на мостик", State.PLAY));
        map.put(4L, new Answer(4L, 2L, "Отказаться подняться на мостик", State.LOSS));
        map.put(5L, new Answer(5L, 3L, "Рассказать правду о себе", State.WIN));
        map.put(6L, new Answer(6L, 3L, "Солгать о себе", State.LOSS));
    }

    @Override
    public Collection<Answer> getAll() {
        return map.values();
    }

    @Override
    public Optional<Answer> get(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public void create(Answer entity) {

    }

    @Override
    public void update(Answer entity) {

    }

    @Override
    public void delete(Answer entity) {

    }
}
