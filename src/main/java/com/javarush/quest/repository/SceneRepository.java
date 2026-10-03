package com.javarush.quest.repository;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.Scene;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SceneRepository {
    private final Map<String, Scene> scenes = new ConcurrentHashMap<>();

    public void save(Scene scene) {
        scenes.put(scene.getId(), scene);
    }

    public Optional<Scene> findById(String sceneId) {
        return Optional.ofNullable(scenes.get(sceneId));
    }
}
