package com.javarush.sharifullina.entity;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getImageShouldReturnFormattedImageNameWithId() {
        User user = User.builder().id(12L).build();

        assertEquals("image-12", user.getImage());
    }

    @Test
    void getStatsShouldLazilyInitializeAndReturnSameMap() {
        User user = new User();

        Map<Quest, WinLossCounter> stats1 = user.getStats();
        assertNotNull(stats1);
        assertTrue(stats1.isEmpty());

        Quest quest = new Quest();
        stats1.put(quest, new WinLossCounter(1, 0));

        assertSame(stats1, user.getStats());
        assertEquals(1, user.getStats().size());
    }

    @Test
    void addWinScoreAndAddLossScoreShouldChangeScoreByOne() {
        User user = User.builder().score(10).build();

        user.addWinScore();
        assertEquals(11, user.getScore());

        user.addLossScore();
        assertEquals(10, user.getScore());
    }
}
