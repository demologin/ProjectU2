package com.javarush.levanov.config;

import com.javarush.levanov.entity.Answer;
import com.javarush.levanov.entity.Quest;
import com.javarush.levanov.entity.Question;
import com.javarush.levanov.repository.AnswerRepository;
import com.javarush.levanov.repository.QuestRepository;
import com.javarush.levanov.repository.QuestionRepository;

public class Config {
    private final QuestRepository questRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public Config(QuestRepository questRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questRepository = questRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public void init() {
        for (Answer answer : answerRepository.getAll()) {
            Long questionId = answer.getQuestionId();
            Question question = questionRepository.get(questionId).get();
            question.setAnswer(answer);
        }
        for (Question question : questionRepository.getAll()) {
            Long questId = question.getQuestId();
            Quest quest = questRepository.get(questId).get();
            quest.setQuestion(question);
        }
    }
}
