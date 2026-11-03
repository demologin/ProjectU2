package com.javarush.zimina.loader;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuestData {
    private String name;
    private String description;
    private String start;
    private List<QuestionData> questions;

}
