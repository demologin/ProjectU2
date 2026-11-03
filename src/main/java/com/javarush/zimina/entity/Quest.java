package com.javarush.zimina.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quest implements Entity {

    private Long id;
    private String name;
    private String description;
    private Long startQuestionId;
    private Long authorId;
}
