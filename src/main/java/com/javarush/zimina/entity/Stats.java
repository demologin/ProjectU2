package com.javarush.zimina.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Stats {
    private final int wins;
    private final int losses;

    public int getTotal() {
        return wins + losses;
    }
}