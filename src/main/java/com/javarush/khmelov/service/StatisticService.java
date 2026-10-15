package com.javarush.khmelov.service;

import com.javarush.khmelov.entity.Game;
import com.javarush.khmelov.entity.State;
import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.repository.GameRepository;
import com.javarush.khmelov.repository.UserRepository;

import java.util.*;

public class StatisticService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public StatisticService(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public Map<User, Map<State, Integer>> getCommonStat() {
        Map<User, Map<State, Integer>> userStateStat = new HashMap<>();
        for (User user : userRepository.getAll()) {
            userStateStat.put(user, getUserStat(user, gameRepository));
        }
        return userStateStat;
    }

    private Map<State, Integer> getUserStat(User user, GameRepository gameRepository) {
        Collection<Game> games = gameRepository.getAll();
        Map<State, Integer> stateMap = new HashMap<>();
        Arrays.stream(State.values())
                .distinct()
                .forEach(state -> {
                    stateMap.put(state, 0);
                });
        games.stream()
                .filter(game -> game.getUser().equals(user))
                .forEach(game -> {
                    stateMap.keySet().stream().filter(state -> state == game.getState())
                            .forEach(state -> {
                                stateMap.put(state, stateMap.get(state) + 1);
                            });
                });
        return stateMap;
    }

    public Map<User, Integer> getUserTotalStat() {
        Map<User, Integer> userTotalStat = new HashMap<>();
        for (User user : userRepository.getAll()) {
            int sum = getUserStat(user, gameRepository).values().stream().mapToInt(Integer::intValue).sum();
            userTotalStat.put(user, sum);
        }
        return userTotalStat;
    }
}

