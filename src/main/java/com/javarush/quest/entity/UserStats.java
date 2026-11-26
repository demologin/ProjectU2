package com.javarush.quest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserStats {
    long playedGames;
    long wins;
    long losses;

}
