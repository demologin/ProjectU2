package com.javarush.quest.entity;

import lombok.Getter;

@Getter
public enum SystemAction {
    RESTART("Начать заново"),
    EXIT("Выйти"),
    BACK("Назад"); //fixme localized text

    private final String text;

    SystemAction(String text) {
        this.text = text;
    }
}
