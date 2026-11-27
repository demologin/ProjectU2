package com.javarush.khmelov.repository;

import com.javarush.khmelov.entity.Question;
import com.javarush.lesson09.SessionCreator;

import java.util.stream.Stream;

public class QuestionRepository extends BaseRepository<Question> {


    public QuestionRepository(SessionCreator sessionCreator) {
        super(Question.class, sessionCreator);
    }
}
