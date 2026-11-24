package com.javarush.sharifullina.init;

import com.javarush.sharifullina.entity.Quest;
import com.javarush.sharifullina.exception.AppException;
import com.javarush.sharifullina.service.CreateQuestService;
import com.javarush.sharifullina.service.ImageService;
import com.javarush.sharifullina.service.QuestService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class InitQuestCreator {
    private final  CreateQuestService service;
    private final QuestService questService;

    public InitQuestCreator(CreateQuestService service, QuestService questService) {
        this.service = service;
        this.questService = questService;
    }

    public void createQuests() {
        List<Path> files = findFiles();
        if (files.isEmpty()) {
            return;
        }
        for (int i = 0; i < files.size(); i++) {
            try {
                Path path = files.get(i);
                String questText = Files.readString(path);
                Quest quest = service.createQuest(questText);

                String fileName = path.getFileName().toString();
                String idStr = fileName.replaceAll("[^0-8]", "");

                if (!idStr.isEmpty()) {
                    Long fileId = Long.parseLong(idStr);
                    quest.setId(fileId);
                }
                questService.update(quest);

            } catch (IOException e) {
                log.error("Ошибка при чтении файла: {}", files.get(i).getFileName(), e);
                throw new AppException("Ошибка при чтении файла: " + files.get(i).getFileName(),e);
            }
        }
    }

    private List<Path> findFiles() {
        try {
            Path textfiles = Paths.get(ImageService.class.getResource("/").toURI())
                    .getParent()
                    .resolve("textfiles");

            if (!Files.exists(textfiles) || !Files.isDirectory(textfiles)) {
                return Collections.emptyList();
            }

            String regex = "^quest-\\d+\\.txt$";

            try (Stream<Path> stream = Files.list(textfiles)) {
                return stream
                        .filter(Files::isRegularFile)
                        .filter(p -> p.getFileName().toString().matches(regex))
                        .collect(Collectors.toList());
            }

        } catch (URISyntaxException | IOException | NullPointerException e) {
            log.error("Ошибка при нахождении файлов квестов",e);
                        return Collections.emptyList();
        }
    }
}























