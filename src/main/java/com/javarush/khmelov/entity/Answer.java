package com.javarush.khmelov.entity;

import lombok.Getter;

@Getter
public class Answer extends Entity {
    private final Long questionId;
    private final String text;
    private final State state;

    public Answer(Long id, Long questionId, String text, State state) {
        this.id = id;
        this.questionId = questionId;
        this.text = text;
        this.state = state;
    }
}
