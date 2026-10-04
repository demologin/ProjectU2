package com.javarush.quest.entity;

import lombok.Data;

import java.util.List;

@Data
public class Scene {
    private String id;
    private String text;
    private List<Option> options;
    private List<SystemAction> systemActions;
    private String questId;

}
