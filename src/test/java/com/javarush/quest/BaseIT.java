package com.javarush.quest;

import com.javarush.quest.config.ApplicationContext;
import com.javarush.quest.config.DataConfig;
import com.javarush.quest.entity.Game;
import com.javarush.quest.entity.GameState;
import com.javarush.quest.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class BaseIT {
    protected final ApplicationContext context;
    protected final HttpServletRequest request;
    protected final HttpServletResponse response;
    protected final HttpSession session;
    protected final User testUser;
    protected final Game testGame;

    protected BaseIT() {
        //Mocks
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //Test data
        context = new ApplicationContext();
        context.getBean(DataConfig.class).loadData();

        testUser = User.builder()
                .id(0L)
                .login("test")
                .password("test")
                .build();
        testGame = Game.builder()
                .userId(testUser.getId())
                .questId("ship")
                .currentSceneId("ship-0")
                .gameState(GameState.PLAYING)
                .build();
    }
}
