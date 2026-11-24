package com.javarush.sharifullina.entity;

import lombok.Data;

import java.util.Map;

@Data
public class Game {
    private Quest quest;
    private Long currentQuestionId;
    private Statement statement;

    public Game(Quest quest) {
        this.quest = quest;
        this.currentQuestionId = 1L;
        statement = Statement.PLAY;
    }

    public Question getCurrentQuestion() {
        Map<Long, Question> questions = quest.getQuestions();
        return questions.get(currentQuestionId);
    }

    public void updateStatement() {
        Question question = getCurrentQuestion();
        statement = question.getStatement();
    }
}
