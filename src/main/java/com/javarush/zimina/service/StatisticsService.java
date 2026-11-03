package com.javarush.zimina.service;

import com.javarush.zimina.entity.GameResult;
import com.javarush.zimina.entity.Stats;
import com.javarush.zimina.repository.GameResultRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

@Slf4j
public class StatisticsService {
    private final GameResultRepository gameResultRepository;

    public StatisticsService(GameResultRepository gameResultRepository) {
        this.gameResultRepository = gameResultRepository;
    }

    public void record(Long questId, Long userId, boolean win) {
        gameResultRepository.save(new GameResult(null, questId, userId, win));
        log.info("Game finished: quest {}, user {}, win {}", questId, userId, win);
    }

    public Stats forQuest(Long questId) {
        List<GameResult> results = gameResultRepository.findAll().stream()
                .filter(result -> Objects.equals(result.getQuestId(), questId))
                .toList();
        return toStats(results);
    }

    public Stats forUser(Long userId) {
        List<GameResult> results = gameResultRepository.findAll().stream()
                .filter(result -> Objects.equals(result.getUserId(), userId))
                .toList();
        return toStats(results);
    }

    private Stats toStats(List<GameResult> results) {
        int wins = (int) results.stream().filter(GameResult::isWin).count();
        return new Stats(wins, results.size() - wins);
    }
}