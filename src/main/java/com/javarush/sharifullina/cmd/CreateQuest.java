package com.javarush.sharifullina.cmd;

import com.javarush.sharifullina.entity.Quest;
import com.javarush.sharifullina.exception.CreateGameException;
import com.javarush.sharifullina.service.CreateQuestService;
import com.javarush.sharifullina.service.ImageService;
import com.javarush.sharifullina.service.QuestService;
import com.javarush.sharifullina.utils.Go;
import com.javarush.sharifullina.utils.HelperRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class CreateQuest implements Command {
    private final CreateQuestService createQuestService;
    private final QuestService questService;

    public CreateQuest(CreateQuestService createQuestService, QuestService questService) {
        this.createQuestService = createQuestService;
        this.questService = questService;
    }
    @Override
    public String doPost(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String questText = request.getParameter("new-quest-text");
        try {
            Quest quest = createQuestService.createQuest(questText);
            questService.create(quest);
            Long id = quest.getId();
            writeTextInFile(questText, id);
        } catch (CreateGameException e) {
            HelperRequest.setError(request, e.getMessage());
            log.error(e.getMessage());
            session.setAttribute("savedQuestText", questText);
            return getView();
        } catch (IOException e) {
            HelperRequest.setError(request, "Что-то пошло не так, не смогли сохранить текст квеста");
            log.error(e.getMessage());
            session.setAttribute("savedQuestText", questText);
            return getView();
        }
        return Go.HOME;
    }
    @SneakyThrows
    private static Path getTextPath() {
        return Paths.get(ImageService.class.getResource("/").toURI())
                .getParent()
                .resolve("textfiles");
    }

    private void writeTextInFile(String questText, Long id) throws IOException {
        Path targetDir = getTextPath();
        if (Files.notExists(targetDir)) {
            Files.createDirectories(targetDir);
        }
        Path fileToSave = targetDir.resolve("quest-" + id + ".txt");
        Files.writeString(fileToSave, questText, java.nio.charset.StandardCharsets.UTF_8);
    }
}
