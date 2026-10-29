package com.javarush.rukavanov.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    private Long id;
    private Long nextQuestionId;
    private String text;
}
