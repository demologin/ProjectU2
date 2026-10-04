package com.javarush.quest.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class Scene implements Entity<String> {
    private String id;
    private String questId;
    private String text;
    private final Collection<Option> options = new ArrayList<>();
    private final Collection<SystemAction> systemActions =  new ArrayList<>();

}
