package com.javarush.khmelov.entity;

import lombok.Getter;

import java.util.LinkedList;

@Getter
public class Quest extends Entity {
    private final String name;
    private final String description;
    private final LinkedList<Question> questions;
    private User author;

    public Quest(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.questions = new LinkedList<>();
    }

    public Question getFirstQuestion() {
        return questions.isEmpty() ? null : questions.get(0);
    }

    public Question getNextQuestion(Long currentQuestionId) {
        Question nextQuestion = null;
        for (int i = 0; i < questions.size() - 1; i++) {
            if (questions.get(i).getId().equals(currentQuestionId)) {
                nextQuestion = questions.get(i + 1);
            }
        }
        return nextQuestion;
    }

    public void setQuestion(Question question) {
        questions.add(question);
    }
}
