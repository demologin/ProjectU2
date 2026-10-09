package com.javarush.quest.service;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.Game;
import com.javarush.quest.repository.GameRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    public void createGame(Long userId, String questId, String sceneId) {
        Game game = Game.builder()
                .userId(userId)
                .questId(questId)
                .currentSceneId(sceneId)
                .build();
        gameRepository.save(game);
    }

    public Optional<Game> findGame(Long userId, String questId) {
        return gameRepository.findBy(userId, questId);
    }

    public void saveGame(Long userId, String questId, String sceneId) {
        findGame(userId, questId).ifPresent(game -> {
            game.setCurrentSceneId(sceneId);
            gameRepository.save(game);
        });
    }

    public void saveGame(Game game) {
        gameRepository.save(game);
    }

    public void deleteGame(Game game) {
        gameRepository.delete(game);
    }
}
