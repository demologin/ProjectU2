package com.javarush.zimina.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question implements Entity {
    private Long id;
    private String title;
    private String text;
    private GameState gameState;
    private String image;
    private List<Answer> answers = new ArrayList<>();
    private Long questId;
}
