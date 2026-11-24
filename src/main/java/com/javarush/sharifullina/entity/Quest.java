package com.javarush.sharifullina.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quest implements EntityID {
    private Long id;
    private String name;
    private Map<Long, Question> questions = new ConcurrentHashMap<>();

    public List<Answer> getAnswers(Long id) {
        return questions.get(id).getAnswers();
    }
}
