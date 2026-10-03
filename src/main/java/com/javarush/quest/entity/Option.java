package com.javarush.quest.entity;

import lombok.Data;

@Data
public class Option {
    private String id;
    private String text;
    private String nextSceneId;

}
