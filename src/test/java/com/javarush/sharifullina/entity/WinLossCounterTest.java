package com.javarush.sharifullina.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WinLossCounterTest {

    @Test
    void addWinShouldReturnNewCounterWithIncrementedWins() {
        WinLossCounter initial = new WinLossCounter(2, 3);
        WinLossCounter updated = initial.addWin();

        assertEquals(3, updated.wins());
        assertEquals(3, updated.losses());
        // Проверка неизменяемости исходного record
        assertEquals(2, initial.wins());
    }

    @Test
    void addLossShouldReturnNewCounterWithIncrementedLosses() {
        WinLossCounter initial = new WinLossCounter(2, 3);
        WinLossCounter updated = initial.addLoss();

        assertEquals(2, updated.wins());
        assertEquals(4, updated.losses());
        assertEquals(3, initial.losses());
    }
}
