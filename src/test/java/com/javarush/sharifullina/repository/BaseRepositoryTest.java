package com.javarush.sharifullina.repository;

import com.javarush.sharifullina.entity.Quest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BaseRepositoryTest {

    private QuestRepository repository;

    @BeforeEach
    void setUp() {
        BaseRepository.id.set(0L);
        repository = new QuestRepository();
    }

    @Test
    void createShouldAssignIncrementedIdAndSaveEntity() {
        Quest quest1 = new Quest();
        quest1.setName("Квест 1");

        Quest quest2 = new Quest();
        quest2.setName("Квест 2");

        Long id1 = repository.create(quest1);
        Long id2 = repository.create(quest2);

        assertEquals(1L, id1);
        assertEquals(1L, quest1.getId());
        assertEquals(2L, id2);
        assertEquals(2L, quest2.getId());
        assertEquals(2, repository.getAll().size());
    }

    @Test
    void getShouldReturnEntityWhenExists() {
        Quest quest = new Quest();
        quest.setName("Тестовый квест");
        Long id = repository.create(quest);

        Optional<Quest> found = repository.get(id);

        assertTrue(found.isPresent());
        assertEquals("Тестовый квест", found.get().getName());
    }

    @Test
    void getShouldReturnEmptyOptionalWhenNotExists() {
        Optional<Quest> found = repository.get(999L);

        assertTrue(found.isEmpty());
    }

    @Test
    void updateShouldModifyExistingEntity() {
        Quest quest = new Quest();
        quest.setName("Старое название");
        Long id = repository.create(quest);

        quest.setName("Новое название");
        repository.update(quest);

        Optional<Quest> updated = repository.get(id);
        assertTrue(updated.isPresent());
        assertEquals("Новое название", updated.get().getName());
    }

    @Test
    void deleteShouldRemoveEntityFromRepository() {
        Quest quest = new Quest();
        Long id = repository.create(quest);

        repository.delete(quest);

        assertTrue(repository.get(id).isEmpty());
        assertTrue(repository.getAll().isEmpty());
    }

    @Test
    void getAllShouldReturnAllSavedEntities() {
        repository.create(new Quest());
        repository.create(new Quest());

        Collection<Quest> all = repository.getAll();

        assertEquals(2, all.size());
    }
}
