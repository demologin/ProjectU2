package com.javarush.quest.service;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.Game;
import com.javarush.quest.entity.GameState;
import com.javarush.quest.entity.UserStats;
import com.javarush.quest.repository.GameRepository;
import lombok.RequiredArgsConstructor;

import java.util.EnumMap;
import java.util.stream.Collectors;

import static com.javarush.quest.entity.GameState.*;

@Component
@RequiredArgsConstructor
public class StatsService {
    private final GameRepository gameRepository;

    public UserStats collectStats(Long userId) {
        EnumMap<GameState, Long> playedGames = gameRepository
                .findAllWithoutState(userId, PLAYING)
                .stream()
                .collect(Collectors.groupingBy(
                        Game::getGameState,
                        () -> new EnumMap<>(GameState.class),
                        Collectors.counting()
                ));
        long wins = playedGames.getOrDefault(WON, 0L);
        long losses = playedGames.getOrDefault(LOST, 0L);
        return new UserStats(wins + losses, wins, losses);
    }
}
