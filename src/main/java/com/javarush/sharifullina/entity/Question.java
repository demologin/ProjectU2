package com.javarush.sharifullina.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question implements EntityID{
    private Long id;
    private String question;
    private List<Answer> answers;
    private Statement  statement;

    public Question(Long id, String question) {
        this.id = id;
        this.question = question;
        answers = new ArrayList<>();
        statement = Statement.PLAY;
    }
}
