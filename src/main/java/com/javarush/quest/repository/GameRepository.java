package com.javarush.quest.repository;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.Game;
import com.javarush.quest.entity.GameState;
import com.javarush.quest.repository.base.BaseRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class GameRepository extends BaseRepository<Game, Long> {
    private final AtomicLong id = new AtomicLong();

    @Override
    protected Long generateId() {
        return id.getAndIncrement();
    }

    public Optional<Game> findFirstBy(Long userId, String questId, GameState gameState) {
        return map.values().stream()
                .filter(game -> game.getUserId().equals(userId))
                .filter(game -> game.getQuestId().equals(questId))
                .filter(game -> game.getGameState() == gameState)
                .findFirst();
    }

    public Collection<Game> findAllWithoutState(Long userId, GameState gameState) {
        return map.values().stream()
                .filter(game -> game.getUserId().equals(userId))
                .filter(game -> game.getGameState() != gameState)
                .toList();
    }
}
