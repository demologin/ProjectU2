package com.javarush.sharifullina.init;

import com.javarush.sharifullina.entity.Quest;
import com.javarush.sharifullina.service.CreateQuestService;
import com.javarush.sharifullina.service.ImageService;
import com.javarush.sharifullina.service.QuestService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InitQuestCreatorTest {

    @Mock
    private CreateQuestService createQuestService;

    @Mock
    private QuestService questService;

    @InjectMocks
    private InitQuestCreator initQuestCreator;

    private Path textfilesDir;
    private Path validQuestFile;
    private Path ignoredFile;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        textfilesDir = Paths.get(ImageService.class.getResource("/").toURI())
                .getParent()
                .resolve("textfiles");
        validQuestFile = textfilesDir.resolve("quest-15.txt");
        ignoredFile = textfilesDir.resolve("readme.txt");
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.deleteIfExists(validQuestFile);
        Files.deleteIfExists(ignoredFile);
    }

    @Test
    void createQuestsShouldDoNothingWhenDirectoryIsEmptyOrMissing() throws Exception {
        Files.deleteIfExists(validQuestFile);
        Files.deleteIfExists(ignoredFile);

        initQuestCreator.createQuests();

        verifyNoInteractions(createQuestService);
        verifyNoInteractions(questService);
    }

    @Test
    void createQuestsShouldParseMatchingFilesSetIdFromFilenameAndSave() throws Exception {
        Files.createDirectories(textfilesDir);
        String questContent = "Тестовый квест\n?1 Вопрос";
        Files.writeString(validQuestFile, questContent);
        Files.writeString(ignoredFile, "Этот файл должен быть проигнорирован");

        Quest parsedQuest = new Quest();
        when(createQuestService.createQuest(questContent)).thenReturn(parsedQuest);

        initQuestCreator.createQuests();

        assertEquals(15L, parsedQuest.getId());
        verify(createQuestService, times(1)).createQuest(questContent);
        verify(questService, times(1)).update(parsedQuest);
    }
}
