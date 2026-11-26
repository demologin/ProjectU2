package com.javarush.quest.util.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.quest.config.annotation.Component;

@Component
public class JsonParser extends JacksonParser {

    public JsonParser() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public boolean canParse(String fileName) {
        return fileName.endsWith(".json");
    }
}
