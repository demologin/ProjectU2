package com.javarush.quest.service;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.Game;
import com.javarush.quest.entity.Scene;
import com.javarush.quest.exception.EntityNotFoundException;
import com.javarush.quest.repository.GameRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    public Optional<Game> findGame(Long userId, String questId) {
        return gameRepository.findBy(userId, questId);
    }

    public void createGame(Long userId, String questId, String sceneId) {
        Game game = Game.builder()
                .userId(userId)
                .questId(questId)
                .currentSceneId(sceneId)
                .build();
        gameRepository.save(game);
    }

    public void saveGame(Long userId, Scene currentScene) {
        Game game = findGame(userId, currentScene.getQuestId())
                .orElseThrow(() -> EntityNotFoundException.of(Game.class));

        game.setGameState(currentScene.getGameState());
        game.setCurrentSceneId(currentScene.getId());
        gameRepository.save(game);
    }

    public void deleteGame(Game game) {
        gameRepository.delete(game);
    }
}
