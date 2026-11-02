package com.javarush.zimina.repository;

import com.javarush.zimina.entity.Question;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class QuestionRepository {
    private final Map<Long, Question> questions = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public void save(Question question) {
        question.setId(nextId.getAndIncrement());
        questions.put(question.getId(), question);
    }

    public Question findById(Long questionId) {
        return questions.get(questionId);
    }

    public List<Question> findByQuestId(Long questId) {
        return questions.values().stream()
                .filter(question -> Objects.equals(question.getQuestId(), questId))
                .toList();
    }

    public void delete(Long id) {
        questions.remove(id);
    }
}
