package com.javarush.zimina.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    private Long id;
    private String text;
    private Long nextQuestionId;
}
