package com.javarush.zimina.service;

import com.javarush.zimina.entity.Answer;
import com.javarush.zimina.entity.GameState;
import com.javarush.zimina.entity.Quest;
import com.javarush.zimina.entity.Question;
import com.javarush.zimina.repository.QuestRepository;
import com.javarush.zimina.repository.QuestionRepository;

import java.util.List;
import java.util.Objects;

public class QuestService {
    private final QuestRepository questRepository;
    private final QuestionRepository questionRepository;

    public QuestService(QuestRepository questRepository, QuestionRepository questionRepository) {
        this.questRepository = questRepository;
        this.questionRepository = questionRepository;
    }

    public List<Quest> getAll() {
        return questRepository.findAll().stream()
                .filter(quest -> quest.getStartQuestionId() != null)
                .toList();
    }

    public Quest getQuest(Long questId) {
        return questRepository.findById(questId);
    }

    public List<Question> getQuestions(Long questId) {
        return questionRepository.findByQuestId(questId);
    }

    public Long create(String name, String description, Long authorId) {
        Quest quest = new Quest(null, name, description, null, authorId);
        questRepository.save(quest);
        return quest.getId();
    }

    public void addQuestion(Long questId, String title, String text, GameState state) {
        Question question = new Question();
        question.setTitle(title);
        question.setText(text);
        question.setGameState(state);
        question.setQuestId(questId);
        questionRepository.save(question);

        Quest quest = questRepository.findById(questId);
        if (quest.getStartQuestionId() == null) {
            quest.setStartQuestionId(question.getId());
        }
    }

    public void addAnswer(Long questId, Long questionId, String text, Long nextQuestionId) {
        Question question = findOwnQuestion(questId, questionId);
        Question next = findOwnQuestion(questId, nextQuestionId);
        if (question != null && next != null) {
            question.getAnswers().add(new Answer(null, text, nextQuestionId));
        }
    }

    public void deleteAnswer(Long questId, Long questionId, int answerIndex) {
        Question question = findOwnQuestion(questId, questionId);
        if (question != null && answerIndex >= 0 && answerIndex < question.getAnswers().size()) {
            question.getAnswers().remove(answerIndex);
        }
    }

    public void deleteQuestion(Long questId, Long questionId) {
        if (findOwnQuestion(questId, questionId) == null) {
            return;
        }
        questionRepository.delete(questionId);

        List<Question> rest = questionRepository.findByQuestId(questId);
        for (Question question : rest) {
            question.getAnswers().removeIf(answer -> Objects.equals(answer.getNextQuestionId(), questionId));
        }

        Quest quest = questRepository.findById(questId);
        if (Objects.equals(quest.getStartQuestionId(), questionId)) {
            quest.setStartQuestionId(rest.isEmpty() ? null : rest.get(0).getId());
        }
    }

    public boolean canEdit(Long questId, Long userId) {
        Quest quest = questRepository.findById(questId);
        return quest != null && userId != null && Objects.equals(quest.getAuthorId(), userId);
    }

    private Question findOwnQuestion(Long questId, Long questionId) {
        Question question = questionRepository.findById(questionId);
        if (question == null || !Objects.equals(question.getQuestId(), questId)) {
            return null;
        }
        return question;
    }
}