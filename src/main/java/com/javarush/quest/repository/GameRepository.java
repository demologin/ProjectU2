package com.javarush.quest.repository;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.Game;
import com.javarush.quest.repository.base.BaseRepository;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class GameRepository extends BaseRepository<Game, Long> {
    private final AtomicLong id = new AtomicLong();

    @Override
    protected Long generateId() {
        return id.getAndIncrement();
    }

    public Optional<Game> findBy(Long userId, String questId) {
        return map.values().stream()
                .filter(game -> game.getUserId().equals(userId))
                .filter(game -> game.getQuestId().equals(questId))
                .findFirst();
    }
}
