package com.javarush.quest.util.data;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.javarush.quest.config.annotation.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class YamlParser implements DataParser {
    private final YAMLMapper mapper = new YAMLMapper();

    @Override
    public boolean canParse(String fileName) {
        String lower = fileName.toLowerCase();
        return lower.endsWith(".yml") || lower.endsWith(".yaml");
    }

    @Override
    public <T> T parse(InputStream stream, Class<T> targetType) throws IOException {
        return mapper.readValue(stream, targetType);
    }

    @Override
    public <T> List<T> parseList(InputStream stream, Class<T> elementType) throws IOException {
        JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, elementType);
        return mapper.readValue(stream, type);
    }
}
