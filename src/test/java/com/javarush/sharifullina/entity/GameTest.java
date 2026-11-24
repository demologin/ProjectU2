package com.javarush.sharifullina.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Quest quest;
    private Question q1;
    private Question q2;

    @BeforeEach
    void setUp() {
        quest = new Quest();
        q1 = new Question(1L, "Первый вопрос");
        q1.setStatement(Statement.PLAY);

        q2 = new Question(2L, "Финал");
        q2.setStatement(Statement.WIN);

        quest.getQuestions().put(1L, q1);
        quest.getQuestions().put(2L, q2);
    }

    @Test
    void constructorShouldInitializeStartingQuestionAndPlayStatement() {
        Game game = new Game(quest);

        assertEquals(quest, game.getQuest());
        assertEquals(1L, game.getCurrentQuestionId());
        assertEquals(Statement.PLAY, game.getStatement());
    }

    @Test
    void getCurrentQuestionShouldReturnQuestionMatchingCurrentId() {
        Game game = new Game(quest);
        assertEquals(q1, game.getCurrentQuestion());

        game.setCurrentQuestionId(2L);
        assertEquals(q2, game.getCurrentQuestion());
    }

    @Test
    void updateStatementShouldSyncStatementWithCurrentQuestion() {
        Game game = new Game(quest);
        game.setCurrentQuestionId(2L);

        game.updateStatement();

        assertEquals(Statement.WIN, game.getStatement());
    }
}
