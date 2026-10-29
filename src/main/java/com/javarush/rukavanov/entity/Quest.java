package com.javarush.rukavanov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quest {

    private Long id;
    private String title;
    private String description;
    private List<Question> questions;
}
