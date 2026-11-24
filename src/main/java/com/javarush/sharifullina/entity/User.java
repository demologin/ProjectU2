package com.javarush.sharifullina.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements EntityID {

    private Long id;
    private String login;
    private String password;
    private Role role;
    private String name;
    private String surname;
    private LocalDate birthday;
    private int score;

    private Map<Quest, WinLossCounter> stats;

    private final Map<Quest, Game> gameMap = new HashMap<>();

    public String getImage() { //TODO move to DTO
        return "image-" + id;
    }

    public Map<Quest, WinLossCounter> getStats() {
        if (this.stats == null) {
            this.stats = new HashMap<>();
        }
        return this.stats;
    }

    public void addWinScore() {
        score++;
    }

    public void addLossScore() {
        score--;
    }
}























