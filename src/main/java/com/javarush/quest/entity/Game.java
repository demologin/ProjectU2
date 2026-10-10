package com.javarush.quest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game implements Entity<Long> {
    private Long id;
    private Long userId;
    private String questId;
    private String currentSceneId;
    private GameState gameState;
    //todo Map<?, ?> questParams; for params specific for each quest?

}
