package com.javarush.quest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Entity<Long> {
    private Long id;
    private String login;
    private String password;

}
