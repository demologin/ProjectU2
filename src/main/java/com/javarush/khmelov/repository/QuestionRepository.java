package com.javarush.khmelov.repository;

import com.javarush.khmelov.entity.Answer;
import com.javarush.khmelov.entity.Quest;
import com.javarush.khmelov.entity.Question;
import com.javarush.khmelov.entity.State;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class QuestionRepository implements Repository<Question> {
    public static final AtomicLong id = new AtomicLong(System.currentTimeMillis());
    private final Map<Long, Question> map = new HashMap<>();

    public QuestionRepository() {
        map.put(1L, new Question(1L, 1L, "Ты потерял память. Принять вызов НЛО?"));
        map.put(2L, new Question(2L, 1L, "Ты принял вызов. Поднимаешься на мостик к капитану?"));
        map.put(3L, new Question(3L, 1L, "Ты поднялся на мостик. Кто ты?"));
    }

    @Override
    public Collection<Question> getAll() {
        return map.values();
    }

    @Override
    public Optional<Question> get(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public void create(Question entity) {

    }

    @Override
    public void update(Question entity) {

    }

    @Override
    public void delete(Question entity) {

    }
}
