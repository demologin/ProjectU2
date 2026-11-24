package com.javarush.sharifullina.cmd;

import com.javarush.sharifullina.entity.Quest;
import com.javarush.sharifullina.service.QuestService;
import com.javarush.sharifullina.utils.Constant;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HomeTest {

    @Mock
    private QuestService questService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private Home home;

    @Test
    void doGetShouldSetQuestsAttributeAndReturnHomeView() {
        List<Quest> quests = List.of(new Quest(), new Quest());
        when(questService.getAll()).thenReturn(quests);

        String result = home.doGet(request);

        assertEquals("home", result);
        verify(request).setAttribute(Constant.QUESTS, quests);
    }
}
