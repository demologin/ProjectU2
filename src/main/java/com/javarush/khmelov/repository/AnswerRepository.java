package com.javarush.khmelov.repository;

import com.javarush.khmelov.entity.Answer;
import com.javarush.lesson09.SessionCreator;

import java.util.stream.Stream;

public class AnswerRepository extends BaseRepository<Answer> {

    public AnswerRepository(SessionCreator sessionCreator) {
        super(Answer.class, sessionCreator);
    }
}
