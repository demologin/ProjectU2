package com.javarush.zimina.repository;

import com.javarush.zimina.entity.Question;

import java.util.List;
import java.util.Objects;

public class QuestionRepository extends BaseRepository<Question> {

    public List<Question> findByQuestId(Long questId) {
        return findAll().stream()
                .filter(question -> Objects.equals(question.getQuestId(), questId))
                .toList();
    }
}
