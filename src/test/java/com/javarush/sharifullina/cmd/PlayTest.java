package com.javarush.sharifullina.cmd;

import com.javarush.sharifullina.entity.*;
import com.javarush.sharifullina.service.QuestService;
import com.javarush.sharifullina.service.UserService;
import com.javarush.sharifullina.utils.Go;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayTest {

    @Mock
    private QuestService questService;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private Play play;

    @BeforeEach
    void setUp() {
        when(request.getSession()).thenReturn(session);
    }

    // ==================== Тесты doGet ====================

    @Test
    void doGetShouldRedirectHomeWhenQuestIdInvalid() {
        when(request.getParameter("questId")).thenReturn("not_a_number");

        String view = play.doGet(request);

        assertEquals("redirect:" + Go.HOME, view);
        verify(session).setAttribute(anyString(), contains("Некорректный формат questId"));
    }

    @Test
    void doGetShouldRedirectHomeWhenQuestIdAndGameAreNull() {
        when(session.getAttribute("userId")).thenReturn(null);
        when(request.getParameter("questId")).thenReturn(null);
        when(session.getAttribute("questId")).thenReturn(null);
        when(session.getAttribute("game")).thenReturn(null);

        String view = play.doGet(request);

        assertEquals("redirect:" + Go.HOME, view);
        verify(session).setAttribute(anyString(), eq("Выберите квест"));
    }


    @Test
    void doGetShouldRedirectHomeWhenQuestNotFound() {
        when(request.getParameter("questId")).thenReturn("42");
        when(questService.get(42L)).thenReturn(null);

        String view = play.doGet(request);

        assertEquals("redirect:" + Go.HOME, view);
        verify(session).setAttribute(anyString(), eq("Квеста с id = 42 не существует"));
    }

    @Test
    void doGetShouldRedirectHomeWhenAuthorizedUserNotFound() {
        Quest quest = createSampleQuest();
        when(session.getAttribute("userId")).thenReturn(99L);
        when(request.getParameter("questId")).thenReturn("1");
        when(questService.get(1L)).thenReturn(quest);
        when(userService.getUser(99L)).thenReturn(null);

        String view = play.doGet(request);

        assertEquals("redirect:" + Go.HOME, view);
        verify(session).setAttribute(anyString(), eq("Игрока с id = 99 не существует"));
    }

    @Test
    void doGetShouldStartNewGameForGuestAndReturnPlayView() {
        Quest quest = createSampleQuest();
        when(session.getAttribute("userId")).thenReturn(null);
        when(request.getParameter("questId")).thenReturn("1");
        when(questService.get(1L)).thenReturn(quest);

        String view = play.doGet(request);

        assertEquals(Go.PLAY, view);
        verify(session).setAttribute(eq("game"), any(Game.class));
        verify(request).setAttribute(eq("question"), eq(quest.getQuestions().get(1L)));
        verify(request).setAttribute("statement", Statement.PLAY);
    }

    @Test
    void doGetShouldSaveNewGameInUserMapWhenAuthorizedUserHasNoSavedGame() {
        Quest quest = createSampleQuest();
        User user = User.builder()
                .id(5L)
                .build();

        when(session.getAttribute("userId")).thenReturn(5L);
        when(request.getParameter("questId")).thenReturn("1");
        when(questService.get(1L)).thenReturn(quest);
        when(userService.getUser(5L)).thenReturn(user);

        String view = play.doGet(request);

        assertEquals(Go.PLAY, view);
        assertTrue(user.getGameMap().containsKey(quest));
        verify(session).setAttribute(eq("game"), eq(user.getGameMap().get(quest)));
    }

    // ==================== Тесты doPost ====================

    @Test
    void doPostShouldReturnHomeWhenGameIsNullInSession() {
        when(session.getAttribute("game")).thenReturn(null);

        String view = play.doPost(request);

        assertEquals(Go.HOME, view);
        verify(session).setAttribute(anyString(), eq("Нет сохраненной игры"));
    }

    @Test
    void doPostShouldReturnHomeWhenNextQuestionIdIsInvalid() {
        Quest quest = createSampleQuest();
        Game game = new Game(quest);
        when(session.getAttribute("game")).thenReturn(game);
        when(request.getParameter("nextQuestionId")).thenReturn("invalid");

        String view = play.doPost(request);

        assertEquals(Go.HOME, view);
        verify(session).setAttribute(anyString(), eq("Что-то пошло не так"));
    }

    @Test
    void doPostShouldUpdateGameStateForGuestPlayer() {
        Quest quest = createSampleQuest();
        Game game = new Game(quest);
        when(session.getAttribute("game")).thenReturn(game);
        when(session.getAttribute("userId")).thenReturn(null);
        when(request.getParameter("nextQuestionId")).thenReturn("2");

        String view = play.doPost(request);

        assertEquals(Go.PLAY, view);
        assertEquals(2L, game.getCurrentQuestionId());
        assertEquals(Statement.WIN, game.getStatement());
        verify(session).removeAttribute("questId");
        verifyNoInteractions(userService);
    }


    @Test
    void doPostShouldUpdateWinStatsAndRemoveGameFromMapWhenUserWins() {
        Quest quest = createSampleQuest();
        Game game = new Game(quest);
        User user = User.builder()
                .id(5L)
                .build();
        user.getGameMap().put(quest, game);

        when(session.getAttribute("game")).thenReturn(game);
        when(session.getAttribute("userId")).thenReturn(5L);
        when(request.getParameter("nextQuestionId")).thenReturn("2");
        when(userService.getUser(5L)).thenReturn(user);

        String view = play.doPost(request);

        assertEquals(Go.PLAY, view);
        assertFalse(user.getGameMap().containsKey(quest));
        assertEquals(1, user.getStats().get(quest).wins());
        assertEquals(0, user.getStats().get(quest).losses());
    }

    @Test
    void doPostShouldUpdateLossStatsAndRemoveGameFromMapWhenUserLoses() {
        Quest quest = createSampleQuest();
        Game game = new Game(quest);
        User user = User.builder()
                .id(5L)
                .build();
        user.getGameMap().put(quest, game);

        when(session.getAttribute("game")).thenReturn(game);
        when(session.getAttribute("userId")).thenReturn(5L);
        when(request.getParameter("nextQuestionId")).thenReturn("3");
        when(userService.getUser(5L)).thenReturn(user);

        String view = play.doPost(request);

        assertEquals(Go.PLAY, view);
        assertFalse(user.getGameMap().containsKey(quest));
        assertEquals(0, user.getStats().get(quest).wins());
        assertEquals(1, user.getStats().get(quest).losses());
    }

    // Вспомогательный метод создания тестового квеста
    private Quest createSampleQuest() {
        Quest quest = new Quest();
        quest.setId(1L);
        quest.setName("Тестовый квест");

        Question q1 = new Question(1L, "Начальный вопрос");
        q1.setStatement(Statement.PLAY);

        Question q2 = new Question(2L, "Победа");
        q2.setStatement(Statement.WIN);

        Question q3 = new Question(3L, "Поражение");
        q3.setStatement(Statement.LOOSE);

        quest.getQuestions().put(1L, q1);
        quest.getQuestions().put(2L, q2);
        quest.getQuestions().put(3L, q3);
        return quest;
    }
}
