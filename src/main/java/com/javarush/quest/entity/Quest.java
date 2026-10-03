package com.javarush.quest.entity;

import lombok.Data;

import java.util.List;

@Data
public class Quest {
    private String id;
    private String name;
    private String text;
    private String description;
    private List<Scene> scenes;

}
