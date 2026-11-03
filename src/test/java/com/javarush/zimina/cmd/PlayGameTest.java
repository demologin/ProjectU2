package com.javarush.zimina.cmd;

import com.javarush.zimina.entity.Question;
import com.javarush.zimina.loader.QuestLoader;
import com.javarush.zimina.repository.GameResultRepository;
import com.javarush.zimina.repository.QuestRepository;
import com.javarush.zimina.repository.QuestionRepository;
import com.javarush.zimina.service.GameService;
import com.javarush.zimina.service.StatisticsService;
import com.javarush.zimina.util.Go;
import com.javarush.zimina.util.Key;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PlayGameTest {

    private HttpServletRequest request;
    private HttpSession session;
    private StatisticsService statisticsService;
    private PlayGame playGame;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        QuestRepository questRepository = new QuestRepository();
        QuestionRepository questionRepository = new QuestionRepository();
        new QuestLoader(questRepository, questionRepository).load("quests/grimoire.json");

        GameService gameService = new GameService(questRepository, questionRepository);
        statisticsService = new StatisticsService(new GameResultRepository());
        playGame = new PlayGame(gameService, statisticsService);
    }

    @Test
    void startPutsFirstQuestionIntoSessionAndRequest() {
        when(request.getParameter(Key.QUEST_ID)).thenReturn("1");

        String view = playGame.doGet(request);

        assertEquals("play-game", view);
        verify(session).setAttribute(Key.QUESTION_ID, 1L);
        verify(request).setAttribute(eq(Key.QUESTION), any(Question.class));
    }

    @Test
    void noGameShowsPageWithoutQuestion() {
        String view = playGame.doGet(request);

        assertEquals("play-game", view);
        verify(request, never()).setAttribute(eq(Key.QUESTION), any());
    }

    @Test
    void answerWithoutGameRedirectsToQuestList() {
        String redirect = playGame.doPost(request);

        assertEquals(Go.LIST_QUEST, redirect);
    }

    @Test
    void normalMoveGoesToNextQuestion() {
        when(session.getAttribute(Key.QUESTION_ID)).thenReturn(1L);
        when(session.getAttribute(Key.QUEST_ID)).thenReturn(1L);
        when(request.getParameter(Key.ANSWER)).thenReturn("1");

        String redirect = playGame.doPost(request);

        assertEquals(Go.PLAY_GAME, redirect);
        verify(session).setAttribute(Key.QUESTION_ID, 2L);
        assertEquals(0, statisticsService.forQuest(1L).getTotal());
    }

    @Test
    void finishedGameIsRecorded() {
        when(session.getAttribute(Key.QUESTION_ID)).thenReturn(1L);
        when(session.getAttribute(Key.QUEST_ID)).thenReturn(1L);
        when(request.getParameter(Key.ANSWER)).thenReturn("0");

        playGame.doPost(request);

        assertEquals(1, statisticsService.forQuest(1L).getLosses());
    }
}