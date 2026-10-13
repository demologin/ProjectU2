package com.javarush.quest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Scene implements Entity<String> {
    private String id;
    private String text;
    private String questId;
    private GameState gameState = GameState.PLAYING;
    private final Collection<Option> options = new ArrayList<>();

}
