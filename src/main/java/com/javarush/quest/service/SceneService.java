package com.javarush.quest.service;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.Option;
import com.javarush.quest.entity.Scene;
import com.javarush.quest.repository.SceneRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SceneService {
    private final SceneRepository sceneRepository;

    public Scene getNextScene(String sceneId, String optionId) {
        Scene currentScene = sceneRepository.findById(sceneId).orElseThrow();

        Option selectedOption = currentScene.getOptions().stream()
                .filter(option -> option.getId().equals(optionId))
                .findFirst().orElseThrow();

        String nextSceneId = selectedOption.getNextSceneId();
        return sceneRepository.findById(nextSceneId).orElseThrow();
    }

    public Scene getScene(String sceneId) {
        return sceneRepository.findById(sceneId).orElseThrow();
    }
}
