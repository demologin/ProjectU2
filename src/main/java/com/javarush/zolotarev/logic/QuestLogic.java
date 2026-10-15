package com.javarush.zolotarev.logic;

public class QuestLogic {
    public static final int FIRST_STEP = 1;

    public Question question(int step) {
        return switch (step) {
            case FIRST_STEP -> new Question(
                    "С чего начнёшь поиск работы?",
                    "Обновишь резюме и портфолио",
                    "Сразу пишешь «возьмите джуна» во все чаты",
                    "Ждёшь, пока сами найдут"
            );
            case 2 -> new Question(
                    "Нашлась вакансия: Junior Java, Tomcat, servlets. Что в отклике?",
                    "Коротко: стек, учебный проект, готовность к тестовому",
                    "Копируешь одно сопроводительное на 50 вакансий",
                    "Пишешь только «interested»"
            );
            case 3 -> new Question(
                    "Дают задание: маленький веб-квест на сервлетах. Как делаешь?",
                    "Maven, чистая логика, JUnit, README",
                    "Сдаёшь один огромный Main без тестов",
                    "Тянешь до дедлайна и сдаёшь пустой репозиторий"
            );
            case 4 -> new Question(
                    "Спрашивают про сессию, JSP и зачем тесты. Ответ?",
                    "Спокойно объясняешь на своих примерах",
                    "«Я гуглил, но забыл» и молчишь",
                    "Споришь, что тесты не нужны"
            );
            default -> throw new IllegalArgumentException("Неизвестный шаг: " + step);
        };
    }

    public Result answer(int step, String choice) {
        String letter = choice.trim().toUpperCase();
        return switch (step) {
            case 1 -> step1(letter);
            case 2 -> step2(letter);
            case 3 -> step3(letter);
            case 4 -> step4(letter);
            default -> throw new IllegalArgumentException("Неизвестный шаг: " + step);
        };
    }

    private Result step1(String letter) {
        return switch (letter) {
            case "A" -> Result.next(2, "Резюме готово. Нашлась вакансия Junior Java, Tomcat, servlets.");
            case "B" -> Result.lose("Вас игнорируют.");
            case "C" -> Result.lose("Тишина в почте.");
            default -> unknown(letter);
        };
    }

    private Result step2(String letter) {
        return switch (letter) {
            case "A" -> Result.next(3, "Отклик прочитали. Дали тестовое: маленький веб-квест на сервлетах.");
            case "B" -> Result.lose("Отказы.");
            case "C" -> Result.lose("Нет ответа.");
            default -> unknown(letter);
        };
    }

    private Result step3(String letter) {
        return switch (letter) {
            case "A" -> Result.next(4, "Тестовое приняли. Собеседование: сессия, JSP и зачем тесты.");
            case "B" -> Result.lose("Отклонили.");
            case "C" -> Result.lose("Пустой репозиторий.");
            default -> unknown(letter);
        };
    }

    private Result step4(String letter) {
        return switch (letter) {
            case "A" -> Result.win("Оффер.");
            case "B" -> Result.lose("Забыл и промолчал.");
            case "C" -> Result.lose("Тесты не нужны — так решил собеседующий.");
            default -> unknown(letter);
        };
    }

    private Result unknown(String letter) {
        throw new IllegalArgumentException("Неизвестный ответ: " + letter);
    }
}
