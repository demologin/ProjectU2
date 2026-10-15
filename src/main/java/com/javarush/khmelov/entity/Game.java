package com.javarush.khmelov.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Game extends Entity {
    private final User user;
    private final Quest quest;
    private State state;

    public Game(User user, Quest quest, State state) {
        this.user = user;
        this.quest = quest;
        this.state = state;
    }
}
