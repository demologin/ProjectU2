package com.javarush.quest.repository;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.entity.Quest;
import com.javarush.quest.repository.base.BaseRepository;

@Component
public class QuestRepository extends BaseRepository<Quest, String> {

}
