package com.javarush.zimina.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameResult {
    private Long id;
    private Long questId;
    private Long userId;
    private boolean win;
}