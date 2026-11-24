package com.javarush.sharifullina.service;

import com.javarush.sharifullina.entity.Quest;
import com.javarush.sharifullina.entity.Question;
import com.javarush.sharifullina.entity.Statement;
import com.javarush.sharifullina.exception.CreateGameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateQuestServiceTest {

    private CreateQuestService createQuestService;

    @BeforeEach
    void setUp() {
        createQuestService = new CreateQuestService();
    }

    @Test
    void createQuestShouldParseValidQuestSuccessfully() {
        String validQuestText = """
                
                Космическое приключение
                ?1 Вы проснулись на корабле. Ваши действия?
                >2 Пойти на мостик
                >3 Вернуться спать
                +2 Вы спасли корабль и победили!
                -3 Корабль столкнулся с астероидом. Поражение.
                """;

        Quest quest = createQuestService.createQuest(validQuestText);

        assertNotNull(quest);
        assertEquals("Космическое приключение", quest.getName());
        assertEquals(3, quest.getQuestions().size());

        Question q1 = quest.getQuestions().get(1L);
        assertEquals(Statement.PLAY, q1.getStatement());
        assertEquals("Вы проснулись на корабле. Ваши действия?", q1.getQuestion());
        assertEquals(2, q1.getAnswers().size());
        assertEquals(2L, q1.getAnswers().get(0).getNextId());
        assertEquals(3L, q1.getAnswers().get(1).getNextId());

        assertEquals(Statement.WIN, quest.getQuestions().get(2L).getStatement());
        assertEquals(Statement.LOOSE, quest.getQuestions().get(3L).getStatement());
    }

    @Test
    void createQuestShouldThrowWhenSignIsInvalid() {
        String text = """
                Тестовый квест
                *1 Неправильный символ в начале
                """;

        CreateGameException ex = assertThrows(CreateGameException.class,
                () -> createQuestService.createQuest(text));
        assertTrue(ex.getMessage().contains("отсутствует идентификатор"));
    }

    @Test
    void createQuestShouldThrowWhenFirstLineIsNotQuestion() {
        String text = """
                Тестовый квест
                +1 Сразу победа без вопроса
                """;

        CreateGameException ex = assertThrows(CreateGameException.class,
                () -> createQuestService.createQuest(text));
        assertTrue(ex.getMessage().contains("идентификатор должен быть ?"));
    }

    @Test
    void createQuestShouldThrowWhenNoSpacesInLine() {
        String text = """
                Тестовый квест
                ?1НетПробела
                """;

        CreateGameException ex = assertThrows(CreateGameException.class,
                () -> createQuestService.createQuest(text));
        assertTrue(ex.getMessage().contains("отсутствуют пробелы"));
    }

    @Test
    void createQuestShouldThrowWhenNoIdAfterSign() {
        String text = """
                Тестовый квест
                ? Пробел сразу после знака
                """;

        CreateGameException ex = assertThrows(CreateGameException.class,
                () -> createQuestService.createQuest(text));
        assertTrue(ex.getMessage().contains("должна идти цифровая метка"));
    }

    @Test
    void createQuestShouldThrowWhenIdIsNotANumber() {
        String text = """
                Тестовый квест
                ?abc Текст вопроса
                """;

        CreateGameException ex = assertThrows(CreateGameException.class,
                () -> createQuestService.createQuest(text));
        assertTrue(ex.getMessage().contains("отсутствует цифровая метка"));
    }

    @Test
    void createQuestShouldThrowWhenTextAfterIdIsEmpty() {
        String text = "Тестовый квест\n?1 ";

        CreateGameException ex = assertThrows(CreateGameException.class,
                () -> createQuestService.createQuest(text));
        assertTrue(ex.getMessage().contains("отсутствует текст вопроса или ответа"));
    }


    @Test
    void createQuestShouldThrowWhenNoQuestionsProvided() {
        String text = """
                Пустой квест
                """;

        CreateGameException ex = assertThrows(CreateGameException.class,
                () -> createQuestService.createQuest(text));
        assertTrue(ex.getMessage().contains("должен содержать вопросы"));
    }

    @Test
    void createQuestShouldThrowWhenQuestionHasLessThanTwoAnswers() {
        String text = """
                Квест с одним ответом
                ?1 Вопрос только с одним вариантом
                >2 Единственный ответ
                +2 Победа
                -3 Поражение
                """;

        CreateGameException ex = assertThrows(CreateGameException.class,
                () -> createQuestService.createQuest(text));
        assertTrue(ex.getMessage().contains("должен иметь минимум 2 ответа"));
    }

    @Test
    void createQuestShouldThrowWhenAnswerPointsToNonExistentQuestion() {
        String text = """
                Квест с битой ссылкой
                ?1 Вопрос
                >2 Ответ на существующий вопрос
                >99 Ответ в никуда
                +2 Победа
                -3 Поражение
                """;

        CreateGameException ex = assertThrows(CreateGameException.class,
                () -> createQuestService.createQuest(text));
        assertTrue(ex.getMessage().contains("ссылается на несуществующий вопрос с id=99"));
    }

    @Test
    void createQuestShouldThrowWhenWinStateIsMissing() {
        String text = """
                Квест без победы
                ?1 Вопрос
                >2 Вариант А
                >3 Вариант Б
                -2 Поражение 1
                -3 Поражение 2
                """;

        CreateGameException ex = assertThrows(CreateGameException.class,
                () -> createQuestService.createQuest(text));
        assertTrue(ex.getMessage().contains("отсутствует победа"));
    }

    @Test
    void createQuestShouldThrowWhenLoseStateIsMissing() {
        String text = """
                Квест без поражения
                ?1 Вопрос
                >2 Вариант А
                >3 Вариант Б
                +2 Победа 1
                +3 Победа 2
                """;

        CreateGameException ex = assertThrows(CreateGameException.class,
                () -> createQuestService.createQuest(text));
        assertTrue(ex.getMessage().contains("отсутствует поражение"));
    }
}
