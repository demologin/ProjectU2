package com.javarush.sharifullina.service;

import com.javarush.sharifullina.entity.Answer;
import com.javarush.sharifullina.entity.Quest;
import com.javarush.sharifullina.entity.Question;
import com.javarush.sharifullina.entity.Statement;
import com.javarush.sharifullina.exception.CreateGameException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CreateQuestService {

    private final String QUESTION = "?";
    private final String ANSWER = ">";
    private final String LOOSE = "-";
    private final String WIN = "+";


    public Quest createQuest(String questText) {
        Quest quest = new Quest();
        try (BufferedReader reader = new BufferedReader(new StringReader(questText))) {
            String line;
            Long currentQuestionIndex = 0L;
            String questName;
            do {
                questName = reader.readLine();
            }while (questName != null && questName.isBlank());
            quest.setName(questName);
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    currentQuestionIndex = doParse(quest, currentQuestionIndex, line);
                }
            }
        } catch (IOException e) {
            throw new CreateGameException("Неправильный формат текста");
        }
        checkQuest(quest);
        return quest;
    }

    private Long doParse(Quest quest, Long currentQuestionIndex, String line) {
        String sign = line.substring(0, 1);

        if (isWrongSign(sign)) {
            throw new CreateGameException("В строке '" + line +"' отсутствует идентификатор вначале вопроса/ответа (?>-+)");
        }
        if (currentQuestionIndex == 0 && !sign.equals(QUESTION)) {
            throw new CreateGameException("В строке '" + line +"' идентификатор должен быть ?");
        }

        int i = line.indexOf(" ");
        if (i == -1) {
            throw new CreateGameException("В строке '" + line +"' отсутствуют пробелы");
        }

        if (i == 1) {
            throw new CreateGameException("В строке '" + line +"' после индификатора должна идти цифровая метка");
        }

        Long idFromText;

        try {
            idFromText = Long.parseLong(line.substring(1, i));
        } catch (NumberFormatException e) {
            throw new CreateGameException("В строке '" + line +"' отсутствует цифровая метка или отсутствует пробел после цифровой метки");
        }

        String text = line.substring(i + 1);
        if (text.isBlank()) {
            throw new CreateGameException("В строке '" + line +"' отсутствует текст вопроса или ответа");
        }

        if (sign.equals(ANSWER)) {
            Answer answer = new Answer();
            answer.setAnswer(text);
            answer.setNextId(idFromText);
            quest.getAnswers(currentQuestionIndex).add(answer);
            return currentQuestionIndex;
        }
        Question question = new Question(idFromText, text);
        switch (sign) {
            case LOOSE: {
                question.setStatement(Statement.LOOSE);
                break;
            }
            case WIN: {
                question.setStatement(Statement.WIN);
                break;
            }
            case QUESTION: {
                question.setStatement(Statement.PLAY);
                break;
            }
        }
        quest.getQuestions().put(question.getId(), question);
        return idFromText;
    }

    private boolean isWrongSign(String sign) {
        return !Set.of(QUESTION, ANSWER, LOOSE, WIN).contains(sign);
    }


    private void checkQuest(Quest quest) {
        Map<Long, Question> questions = quest.getQuestions();
        if (questions == null || questions.isEmpty()) {
            throw new CreateGameException("Квест '" + quest.getName() + "' должен содержать вопросы");
        }
        boolean hasWin = false;
        boolean hasLose = false;

        for (Question question : questions.values()) {
            Statement state = question.getStatement();

            if (state == Statement.WIN) {
                hasWin = true;
                continue;
            }
            if (state == Statement.LOOSE) {
                hasLose = true;
                continue;
            }

            List<Answer> answers = question.getAnswers();
            if (answers == null || answers.size() < 2) {
                throw new CreateGameException("Квест '" + quest.getName() + "': вопрос id = " + question.getId() + " должен иметь минимум 2 ответа.");
            }
            for (Answer answer : answers) {
                Long nextId = answer.getNextId();
                if (nextId == null || !questions.containsKey(nextId)) {
                    throw new CreateGameException(
                            "Квест '" + quest.getName() + "': oтвет '" + answer.getAnswer() + "' в вопросе id=" + question.getId() +
                                    " ссылается на несуществующий вопрос с id=" + nextId
                    );
                }
            }
        }
        if (!hasWin) {
            throw new CreateGameException("В квесте '" + quest.getName() + "' отсутствует победа.");
        }
        if (!hasLose) {
            throw new CreateGameException("В квесте '" + quest.getName() + "' отсутствует поражение.");
        }
    }
}
