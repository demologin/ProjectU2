package com.javarush.zimina.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.zimina.entity.Answer;
import com.javarush.zimina.entity.Quest;
import com.javarush.zimina.entity.Question;
import com.javarush.zimina.repository.QuestRepository;
import com.javarush.zimina.repository.QuestionRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class QuestLoader {
    private final QuestRepository questRepository;
    private final QuestionRepository questionRepository;

    public QuestLoader(QuestRepository questRepository, QuestionRepository questionRepository) {
        this.questRepository = questRepository;
        this.questionRepository = questionRepository;
    }

    public void load(String path) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(path)) {
            if (input == null) {
                throw new RuntimeException("Файл квеста не найден: " + path);
            }
            QuestData data = mapper.readValue(input, QuestData.class);

            // 1. Квест: сохраняем сразу, чтобы получить его номер
            Quest quest = new Quest(null, data.getName(), data.getDescription(), null, null);
            questRepository.save(quest);

            // 2. Вопросы: создаём, привязываем к квесту, запоминаем по метке
            Map<String, Question> byKey = new HashMap<>();
            for (QuestionData questionData : data.getQuestions()) {
                Question question = new Question();
                question.setTitle(questionData.getTitle());
                question.setText(questionData.getText());
                question.setGameState(questionData.getState());
                question.setImage(questionData.getImage());
                question.setQuestId(quest.getId());
                questionRepository.save(question);
                byKey.put(questionData.getKey(), question);
            }

            // 3. Ответы: метку "next" превращаем в номер вопроса
            for (QuestionData questionData : data.getQuestions()) {
                Question question = byKey.get(questionData.getKey());
                for (AnswerData answerData : questionData.getAnswers()) {
                    Question next = byKey.get(answerData.getNext());
                    if (next == null) {
                        throw new RuntimeException("Нет вопроса с меткой: " + answerData.getNext());
                    }
                    question.getAnswers().add(new Answer(null, answerData.getText(), next.getId()));
                }
            }

            // 4. Стартовый вопрос
            quest.setStartQuestionId(byKey.get(data.getStart()).getId());

        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать квест: " + path, e);
        }
    }
}