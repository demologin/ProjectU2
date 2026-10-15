package com.javarush.khmelov.entity;

import lombok.Getter;

import java.util.LinkedList;

@Getter
public class Question extends Entity {
    private final Long questId;
    private final String text;
    private final LinkedList<Answer> answers;

    public Question(Long id, Long questId, String text) {
        this.id = id;
        this.questId = questId;
        this.text = text;
        this.answers = new LinkedList<>();
    }

    public void setAnswer(Answer answer){
        this.answers.add(answer);
    }
}
