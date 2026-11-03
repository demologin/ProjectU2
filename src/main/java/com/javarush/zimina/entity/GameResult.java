package com.javarush.zimina.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameResult implements Entity {
    private Long id;
    private Long questId;
    private Long userId;
    private boolean win;
}