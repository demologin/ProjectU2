package com.javarush.zimina.cmd;

import com.javarush.zimina.entity.GameState;
import com.javarush.zimina.entity.Question;
import com.javarush.zimina.entity.User;
import com.javarush.zimina.service.QuestService;
import com.javarush.zimina.util.Go;
import com.javarush.zimina.util.Key;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditQuest implements Command {
    private final QuestService questService;

    public EditQuest(QuestService questService) {
        this.questService = questService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Key.USER);
        if (user == null) {
            return "login";
        }
        Long questId = Long.valueOf(request.getParameter(Key.QUEST_ID));
        if (!questService.canEdit(questId, user)) {
            return getView();
        }

        List<Question> questions = questService.getQuestions(questId);
        Map<Long, String> titles = new HashMap<>();
        for (Question question : questions) {
            titles.put(question.getId(), question.getTitle());
        }

        request.setAttribute(Key.QUEST, questService.getQuest(questId));
        request.setAttribute(Key.QUESTIONS, questions);
        request.setAttribute(Key.TITLES, titles);
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Key.USER);
        Long questId = Long.valueOf(request.getParameter(Key.QUEST_ID));
        if (user == null || !questService.canEdit(questId, user)) {
            return Go.LIST_QUEST;
        }

        String action = request.getParameter(Key.ACTION);
        String text = request.getParameter(Key.TEXT);

        if ("question".equals(action)) {
            String title = request.getParameter(Key.TITLE);
            GameState state = GameState.valueOf(request.getParameter(Key.STATE));
            if (title != null && !title.isBlank()) {
                questService.addQuestion(questId, title.trim(), text, state);
            }
        } else if ("answer".equals(action)) {
            Long questionId = Long.valueOf(request.getParameter(Key.QUESTION_ID));
            Long next = Long.valueOf(request.getParameter(Key.NEXT));
            if (text != null && !text.isBlank()) {
                questService.addAnswer(questId, questionId, text.trim(), next);
            }
        } else if ("deleteAnswer".equals(action)) {
            Long questionId = Long.valueOf(request.getParameter(Key.QUESTION_ID));
            int answerIndex = Integer.parseInt(request.getParameter(Key.ANSWER));
            questService.deleteAnswer(questId, questionId, answerIndex);
        } else if ("deleteQuestion".equals(action)) {
            Long questionId = Long.valueOf(request.getParameter(Key.QUESTION_ID));
            questService.deleteQuestion(questId, questionId);
        }
        return Go.EDIT_QUEST + "?" + Key.QUEST_ID + "=" + questId;
    }
}