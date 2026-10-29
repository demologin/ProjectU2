package com.javarush.rukavanov.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.rukavanov.entity.Quest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class QuestParser {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private InputStream getQuestStream(String file) throws IOException {

        InputStream inputStream = QuestParser.class.getClassLoader()
                .getResourceAsStream("quests/" + file);

        if (inputStream == null) {
            throw new IOException("Не удалось найти файл " + file);
        }
        return inputStream;

    }

    private Quest parseQuest(String file) throws IOException {
        try (InputStream inputStream = getQuestStream(file)) {
            return objectMapper.readValue(inputStream, Quest.class);
        }
    }

    private Path getQuestsDirectory() throws IOException {
        URL resourceUrl = QuestParser.class.getClassLoader()
                .getResource("quests");

        if (resourceUrl == null) {
            throw new IOException("Папка quests не найдена");
        }

        if (!resourceUrl.getProtocol().equals("file")) {
            throw new IOException("Неподдерживаемый формат ресурсов: "
                    + resourceUrl.getProtocol());
        }

        return Paths.get(resourceUrl.getPath());
    }

    private List<String> getQuestFiles() throws IOException {
        Path directory = getQuestsDirectory();

        try (Stream<Path> files = Files.list(directory)) {
            return files
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .map(path -> path.getFileName().toString())
                    .toList();

        }
    }

    public List<Quest> parseAllQuests() throws IOException {
        List<Quest> quests = new ArrayList<>();
        List<String> files = getQuestFiles();
        for (String fileName : files) {
            quests.add(parseQuest(fileName));

        }
        return quests;
    }

}
