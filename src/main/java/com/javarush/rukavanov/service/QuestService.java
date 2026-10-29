package com.javarush.rukavanov.service;

import com.javarush.rukavanov.entity.*;
import com.javarush.rukavanov.repository.QuestRepository;
import com.javarush.rukavanov.util.QuestParser;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class QuestService {

    private final QuestRepository questRepository;
    private static final Long VICTORY_ID = 777L;
    private static final Long DEFEAT_ID = 999L;

    public QuestService() throws IOException {
        this.questRepository = new QuestRepository(new QuestParser());
    }

    public List<Quest> findAll() {
        return questRepository.findAll();
    }

    public Optional<Quest> findById(Long id) {
        return questRepository.findById(id);
    }

    public QuestResult findNextQuestion(Long questId, Long answerId) {
        Quest quest = questRepository.findById(questId)
                .orElseThrow(() -> new IllegalArgumentException("Квест не найден: " + questId));
        List<Question> questions = quest.getQuestions();
        for (Question question : questions) {
            for (Answer answer : question.getAnswers()) {
                if (answerId.equals(answer.getId())) {
                    Long nextQuestionId = answer.getNextQuestionId();
                    if (nextQuestionId.equals(VICTORY_ID)) {
                        return new QuestResult(QuestStatus.VICTORY, null);
                    } else if (nextQuestionId.equals(DEFEAT_ID)) {
                        return new QuestResult(QuestStatus.DEFEAT, null);
                    } else {
                        for (Question nextQuestion : questions) {
                            if (nextQuestion.getId().equals(nextQuestionId)) {
                                return new QuestResult(QuestStatus.IN_PROGRESS, nextQuestion);
                            }
                        }
                    }

                }
            }
        }
        throw new IllegalArgumentException("Ответ не найден: " + answerId);
    }
}
