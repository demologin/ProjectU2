package com.javarush.zimina.loader;

import com.javarush.zimina.entity.GameState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuestionData {
    private String key;
    private String title;
    private String text;
    private GameState state;
    private String image;
    private List<AnswerData> answers;
}
