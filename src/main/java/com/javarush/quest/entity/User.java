package com.javarush.quest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Entity<Long> {
    private Long id;
    private String login;
    private String password;
    private Collection<Game> games;

}
