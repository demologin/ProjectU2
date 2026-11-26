package com.javarush.quest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quest implements Entity<String> {
    private String id;
    private String name;
    private String text;
    private String description;
    private String startSceneId;
    private final Collection<Scene> scenes = new ArrayList<>();

}
