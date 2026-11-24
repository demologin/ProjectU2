package com.javarush.sharifullina.entity;

import lombok.Data;

@Data
public class Answer implements EntityID{
    private Long id;
    private Long nextId;
    private String answer;
}
