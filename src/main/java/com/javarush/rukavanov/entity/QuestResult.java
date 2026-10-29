package com.javarush.rukavanov.entity;

import lombok.Getter;

@Getter
public class QuestResult {
    private final QuestStatus questStatus;
    private final Question nextQuestion;

    public QuestResult(QuestStatus questStatus, Question nextQuestion) {
        this.questStatus = questStatus;
        this.nextQuestion = nextQuestion;
    }


}
