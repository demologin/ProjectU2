package com.javarush.sharifullina;

import com.javarush.sharifullina.cmd.Command;
import com.javarush.sharifullina.config.Winter;
import com.javarush.sharifullina.controller.HttpResolver;
import com.javarush.sharifullina.entity.Quest;
import com.javarush.sharifullina.entity.Statement;
import com.javarush.sharifullina.entity.User;
import com.javarush.sharifullina.repository.BaseRepository;
import com.javarush.sharifullina.service.CreateQuestService;
import com.javarush.sharifullina.service.QuestService;
import com.javarush.sharifullina.service.UserService;
import com.javarush.sharifullina.utils.Constant;
import com.javarush.sharifullina.utils.Go;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class QuestFlowIT {

    private HttpResolver httpResolver;
    private UserService userService;
    private QuestService questService;
    private CreateQuestService createQuestService;

    private HttpServletRequest request;
    private HttpSession session;
    private Map<String, Object> sessionStorage;

    @BeforeEach
    void setUp() {
        BaseRepository.id.set(0L);

        // Получаем реальные бины из вашего DI-контейнера Winter
        httpResolver = Winter.getBean(HttpResolver.class);
        userService = Winter.getBean(UserService.class);
        questService = Winter.getBean(QuestService.class);
        createQuestService = Winter.getBean(CreateQuestService.class);

        // Очищаем репозитории перед тестом для полной изоляции
        userService.getAll().forEach(userService::deleteUser);
        questService.getAll().forEach(questService::delete);

        // Имитируем реальное хранилище атрибутов HttpSession в памяти
        sessionStorage = new HashMap<>();
        session = mock(HttpSession.class);
        request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(session);

        doAnswer(inv -> {
            sessionStorage.put(inv.getArgument(0), inv.getArgument(1));
            return null;
        }).when(session).setAttribute(anyString(), any());

        when(session.getAttribute(anyString())).thenAnswer(inv -> sessionStorage.get(inv.getArgument(0)));

        doAnswer(inv -> {
            sessionStorage.remove(inv.getArgument(0));
            return null;
        }).when(session).removeAttribute(anyString());
    }

    @Test
    void fullUserScenarioSignUpLogInAndWinQuest() {
        // 1. Подготовка квеста через реальный CreateQuestService и сохранение в QuestRepository
        String questScript = """
                Интеграционный квест
                ?1 Начало пути. Куда идти?
                >2 Налево к победе
                >3 Направо к поражению
                +2 Вы нашли сокровище!
                -3 Вы упали в яму.
                """;
        Quest quest = createQuestService.createQuest(questScript);
        questService.create(quest);
        assertNotNull(quest.getId());

        // 2. Регистрация нового пользователя через команду /sign-up
        when(request.getRequestURI()).thenReturn("/sign-up");
        when(request.getParameter(Constant.LOGIN)).thenReturn("integration_user");
        when(request.getParameter(Constant.PASSWORD_FIRST)).thenReturn("pass123");
        when(request.getParameter(Constant.PASSWORD_SECOND)).thenReturn("pass123");

        Command signUpCmd = httpResolver.resolve(request);
        String signUpResult = signUpCmd.doPost(request);

        assertEquals(Go.PRIVATE_PROFILE_EDIT, signUpResult);
        Long createdUserId = (Long) sessionStorage.get("userId");
        assertNotNull(createdUserId);

        // 3. Запуск квеста через GET /play?questId=...
        when(request.getRequestURI()).thenReturn("/play");
        when(request.getParameter("questId")).thenReturn(String.valueOf(quest.getId()));

        Command playCmd = httpResolver.resolve(request);
        String playGetResult = playCmd.doGet(request);

        assertEquals(Go.PLAY, playGetResult);
        assertNotNull(sessionStorage.get("game"));

        // 4. Выбор победного ответа через POST /play (nextQuestionId = 2)
        when(request.getParameter("nextQuestionId")).thenReturn("2");
        String playPostResult = playCmd.doPost(request);

        assertEquals(Go.PLAY, playPostResult);
        verify(request).setAttribute("statement", Statement.PLAY);

        // 5. Проверяем, что статистика и рейтинг пользователя реально обновились в UserRepository
        User updatedUser = userService.getUser(createdUserId);
        assertEquals(1, updatedUser.getScore());
        assertEquals(1, updatedUser.getStats().get(quest).wins());
        assertEquals(0, updatedUser.getStats().get(quest).losses());
        assertTrue(updatedUser.getGameMap().isEmpty(), "Завершённая игра должна удалиться из активных");
    }
}
