package com.javarush.quest.util.data;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.javarush.quest.config.annotation.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class YamlDataParser implements DataParser {
    private final YAMLMapper mapper = new YAMLMapper();

    @Override
    public <T> List<T> parse(InputStream stream, Class<T> elementType) {
        JavaType collectionType = mapper.getTypeFactory()
                .constructCollectionType(List.class, elementType);

        try {
            return mapper.readValue(stream, collectionType);
        } catch (IOException e) {
            throw new RuntimeException("Parsing exception", e);
        }
    }

    @Override
    public boolean supports(String resourceName) {
        if (resourceName == null) {
            throw new IllegalArgumentException("Resource name is null");
        }
        String lower = resourceName.toLowerCase();
        return lower.endsWith(".yml") || lower.endsWith(".yaml");
    }
}
