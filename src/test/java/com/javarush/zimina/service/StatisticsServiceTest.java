package com.javarush.zimina.service;

import com.javarush.zimina.entity.Stats;
import com.javarush.zimina.repository.GameResultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticsServiceTest {

    private StatisticsService statisticsService;

    @BeforeEach
    void setUp() {
        statisticsService = new StatisticsService(new GameResultRepository());
    }

    @Test
    void emptyStatsWhenNoGames() {
        Stats stats = statisticsService.forQuest(1L);

        assertEquals(0, stats.getTotal());
    }

    @Test
    void countsWinsAndLossesForQuest() {
        statisticsService.record(1L, null, true);
        statisticsService.record(1L, null, false);
        statisticsService.record(1L, null, false);

        Stats stats = statisticsService.forQuest(1L);

        assertEquals(1, stats.getWins());
        assertEquals(2, stats.getLosses());
        assertEquals(3, stats.getTotal());
    }

    @Test
    void otherQuestNotCounted() {
        statisticsService.record(1L, null, true);
        statisticsService.record(2L, null, true);

        assertEquals(1, statisticsService.forQuest(1L).getTotal());
    }

    @Test
    void userStatsCountOnlyOwnGames() {
        statisticsService.record(1L, 10L, true);
        statisticsService.record(1L, 20L, false);

        Stats stats = statisticsService.forUser(10L);

        assertEquals(1, stats.getWins());
        assertEquals(0, stats.getLosses());
    }

    @Test
    void guestGamesCountForQuestOnly() {
        statisticsService.record(1L, null, true);

        assertEquals(1, statisticsService.forQuest(1L).getTotal());
        assertEquals(0, statisticsService.forUser(10L).getTotal());
    }

    @Test
    void largeIdsComparedCorrectly() {
        statisticsService.record(1000L, 1000L, true);

        assertEquals(1, statisticsService.forQuest(1000L).getTotal());
        assertEquals(1, statisticsService.forUser(1000L).getTotal());
    }
}