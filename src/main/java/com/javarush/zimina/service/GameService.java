package com.javarush.zimina.service;

import com.javarush.zimina.entity.Answer;
import com.javarush.zimina.entity.Quest;
import com.javarush.zimina.entity.Question;
import com.javarush.zimina.repository.QuestRepository;
import com.javarush.zimina.repository.QuestionRepository;

public class GameService {
    private final QuestRepository questRepository;
    private final QuestionRepository questionRepository;

    public GameService(QuestRepository questRepository, QuestionRepository questionRepository) {
        this.questRepository = questRepository;
        this.questionRepository = questionRepository;
    }

    public Question start(Long questId) {
        Quest quest = questRepository.findById(questId);
        return questionRepository.findById(quest.getStartQuestionId());
    }

    public Question move(Long currentQuestionId, int answerIndex) {
        Question current = questionRepository.findById(currentQuestionId);
        Answer answer = current.getAnswers().get(answerIndex);
        return questionRepository.findById(answer.getNextQuestionId());
    }

    public Question getQuestion(Long id) {
        return questionRepository.findById(id);
    }
}
