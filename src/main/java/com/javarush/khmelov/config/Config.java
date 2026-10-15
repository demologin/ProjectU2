package com.javarush.khmelov.config;

import com.javarush.khmelov.entity.Answer;
import com.javarush.khmelov.entity.Quest;
import com.javarush.khmelov.entity.Question;
import com.javarush.khmelov.repository.AnswerRepository;
import com.javarush.khmelov.repository.QuestRepository;
import com.javarush.khmelov.repository.QuestionRepository;

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
