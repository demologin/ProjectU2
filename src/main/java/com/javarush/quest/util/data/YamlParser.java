package com.javarush.quest.util.data;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.javarush.quest.config.annotation.Component;

@Component
public class YamlParser extends JacksonParser {

    public YamlParser() {
        this.mapper = new YAMLMapper();
    }

    @Override
    public boolean canParse(String fileName) {
        String lower = fileName.toLowerCase();
        return lower.endsWith(".yml") || lower.endsWith(".yaml");
    }
}
