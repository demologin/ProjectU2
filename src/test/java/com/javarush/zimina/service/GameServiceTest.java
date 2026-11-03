package com.javarush.zimina.service;

import com.javarush.zimina.entity.GameState;
import com.javarush.zimina.entity.Question;
import com.javarush.zimina.loader.QuestLoader;
import com.javarush.zimina.repository.QuestRepository;
import com.javarush.zimina.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameServiceTest {

    private GameService gameService;

    @BeforeEach
    void setUp() {
        QuestRepository questRepository = new QuestRepository();
        QuestionRepository questionRepository = new QuestionRepository();
        new QuestLoader(questRepository, questionRepository).load("quests/grimoire.json");
        gameService = new GameService(questRepository, questionRepository);
    }

    @Test
    void startReturnsFirstQuestion() {
        Question first = gameService.start(1L);

        assertEquals("Пробуждение", first.getTitle());
        assertEquals(GameState.PLAY, first.getGameState());
    }

    @Test
    void readingLeadsToSecondChapter() {
        Question first = gameService.start(1L);
        Question second = gameService.move(first.getId(), 1);

        assertEquals("Надпись на пергаменте", second.getTitle());
    }

    @Test
    void escapeLeadsToLose() {
        Question first = gameService.start(1L);
        Question next = gameService.move(first.getId(), 0);

        assertEquals(GameState.LOSE, next.getGameState());
    }

    @Test
    void correctPathLeadsToWin() {
        Question question = gameService.start(1L);
        question = gameService.move(question.getId(), 1);
        question = gameService.move(question.getId(), 1);
        question = gameService.move(question.getId(), 2);
        question = gameService.move(question.getId(), 0);
        question = gameService.move(question.getId(), 0);

        assertEquals(GameState.WIN, question.getGameState());
        assertEquals("Проклятие снято", question.getTitle());
    }

    @Test
    void getQuestionReturnsQuestionById() {
        Question first = gameService.start(1L);
        Question found = gameService.getQuestion(first.getId());

        assertEquals(first.getTitle(), found.getTitle());
    }

    @Test
    void wrongAnswerIndexThrows() {
        Question first = gameService.start(1L);

        assertThrows(IndexOutOfBoundsException.class,
                () -> gameService.move(first.getId(), 5));
    }
}