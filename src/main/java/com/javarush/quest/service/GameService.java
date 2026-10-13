package com.javarush.quest.service;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.Game;
import com.javarush.quest.entity.Scene;
import com.javarush.quest.entity.User;
import com.javarush.quest.exception.impl.EntityNotFoundException;
import com.javarush.quest.repository.GameRepository;
import com.javarush.quest.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.javarush.quest.entity.GameState.PLAYING;

@Component
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public Optional<Game> findGame(Long userId, String questId) {
        return gameRepository.findFirstBy(userId, questId, PLAYING);
    }

    public void createGame(Long userId, String questId, String sceneId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> EntityNotFoundException.of(User.class));
        Game game = Game.builder().userId(userId).questId(questId)
                .currentSceneId(sceneId).gameState(PLAYING).build();

        gameRepository.save(game);
        user.getGames().add(game);
        //userRepository.save(user); -- redundant with in-memory repo
    }

    public void saveGame(Long userId, Scene currentScene) {
        Game game = gameRepository.findFirstBy(userId, currentScene.getQuestId(), PLAYING)
                .orElseThrow(() -> EntityNotFoundException.of(Game.class));

        game.setGameState(currentScene.getGameState());
        game.setCurrentSceneId(currentScene.getId());
        //gameRepository.save(game); -- redundant with in-memory repo
    }
}
