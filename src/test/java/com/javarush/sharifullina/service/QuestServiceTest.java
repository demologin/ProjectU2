package com.javarush.sharifullina.service;

import com.javarush.sharifullina.entity.Quest;
import com.javarush.sharifullina.repository.QuestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestServiceTest {

    @Mock
    private QuestRepository questRepository;

    @InjectMocks
    private QuestService questService;

    @Test
    void createShouldDelegateToRepository() {
        Quest quest = new Quest();
        questService.create(quest);

        verify(questRepository, times(1)).create(quest);
    }

    @Test
    void updateShouldDelegateToRepository() {
        Quest quest = new Quest();
        questService.update(quest);

        verify(questRepository, times(1)).update(quest);
    }

    @Test
    void deleteShouldDelegateToRepository() {
        Quest quest = new Quest();
        questService.delete(quest);

        verify(questRepository, times(1)).delete(quest);
    }

    @Test
    void getAllShouldReturnAllQuests() {
        List<Quest> expectedQuests = List.of(new Quest(), new Quest());
        when(questRepository.getAll()).thenReturn(expectedQuests);

        Collection<Quest> actualQuests = questService.getAll();

        assertEquals(expectedQuests, actualQuests);
        verify(questRepository).getAll();
    }

    @Test
    void getShouldReturnQuestWhenFound() {
        Quest expectedQuest = new Quest();
        when(questRepository.get(1L)).thenReturn(Optional.of(expectedQuest));

        Quest actualQuest = questService.get(1L);

        assertEquals(expectedQuest, actualQuest);
    }

    @Test
    void getShouldReturnNullWhenNotFound() {
        when(questRepository.get(999L)).thenReturn(Optional.empty());

        Quest actualQuest = questService.get(999L);

        assertNull(actualQuest);
    }
}
