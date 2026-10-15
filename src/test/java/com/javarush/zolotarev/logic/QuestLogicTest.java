package com.javarush.zolotarev.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestLogicTest {

    private QuestLogic logic;

    @BeforeEach
    void setUp() {
        logic = new QuestLogic();
    }

    @Test
    void answerAOnSteps1To3GoesForward() {
        Result first = logic.answer(1, "A");
        Result second = logic.answer(2, "A");
        Result third = logic.answer(3, "A");

        assertFalse(first.isFinished());
        assertEquals(2, first.getNextStep());
        assertFalse(second.isFinished());
        assertEquals(3, second.getNextStep());
        assertFalse(third.isFinished());
        assertEquals(4, third.getNextStep());
    }

    @Test
    void answerAOnStep4Wins() {
        Result result = logic.answer(4, "A");

        assertTrue(result.isFinished());
        assertTrue(result.isWon());
        assertEquals("Оффер.", result.getMessage());
    }

    @Test
    void losingAnswersFinishWithoutWin() {
        assertLose(1, "B");
        assertLose(1, "C");
        assertLose(2, "B");
        assertLose(2, "C");
        assertLose(3, "B");
        assertLose(3, "C");
        assertLose(4, "B");
        assertLose(4, "C");
    }

    @Test
    void eachStepHasQuestion() {
        assertEquals("С чего начнёшь поиск работы?", logic.question(1).getText());
        assertEquals("Нашлась вакансия: Junior Java, Tomcat, servlets. Что в отклике?", logic.question(2).getText());
        assertEquals("Дают задание: маленький веб-квест на сервлетах. Как делаешь?", logic.question(3).getText());
        assertEquals("Спрашивают про сессию, JSP и зачем тесты. Ответ?", logic.question(4).getText());
        assertThrows(IllegalArgumentException.class, () -> logic.question(9));
    }

    @Test
    void unknownStepOrChoiceThrows() {
        assertThrows(IllegalArgumentException.class, () -> logic.answer(9, "A"));
        assertThrows(IllegalArgumentException.class, () -> logic.answer(1, "D"));
    }

    private void assertLose(int step, String choice) {
        Result result = logic.answer(step, choice);
        assertTrue(result.isFinished());
        assertFalse(result.isWon());
    }
}