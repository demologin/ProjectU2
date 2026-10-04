package com.javarush.quest.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class Quest implements Entity<String> {
    private String id;
    private String name;
    private String text;
    private String description;
    private final Collection<Scene> scenes = new ArrayList<>();

}
