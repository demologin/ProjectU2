package com.javarush.quest.repository;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.Scene;
import com.javarush.quest.repository.base.BaseRepository;

import java.util.UUID;

@Component
public class SceneRepository extends BaseRepository<Scene, String> {

    @Override
    protected String generateId() {
        return UUID.randomUUID().toString();
    }
}
