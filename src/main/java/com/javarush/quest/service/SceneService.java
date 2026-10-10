package com.javarush.quest.service;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.Scene;
import com.javarush.quest.exception.EntityNotFoundException;
import com.javarush.quest.repository.SceneRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SceneService {
    private final SceneRepository sceneRepository;

    public Scene getScene(String sceneId) {
        return sceneRepository.findById(sceneId)
                .orElseThrow(() -> EntityNotFoundException.of(Scene.class));
    }
}
