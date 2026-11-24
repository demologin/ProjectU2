package com.javarush.sharifullina.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestTest {

    @Test
    void getAnswersShouldReturnAnswersListForSpecificQuestionId() {
        Quest quest = new Quest();
        Question question = new Question(1L, "Вопрос");
        Answer answer = new Answer();
        answer.setAnswer("Ответ 1");
        question.getAnswers().add(answer);

        quest.getQuestions().put(1L, question);

        List<Answer> answers = quest.getAnswers(1L);
        assertEquals(1, answers.size());
        assertEquals("Ответ 1", answers.get(0).getAnswer());
    }
}
