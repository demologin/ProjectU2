package com.javarush.quest.util;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.util.data.DataParser;
import com.javarush.quest.util.data.YamlDataParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class ResourceLoader {
    private final List<DataParser> parsers = new ArrayList<>();

    public ResourceLoader() {
        parsers.add(new YamlDataParser()); //todo bean injection?
    }

    public <T> List<T> load(String resource, Class<T> elementType) {
        DataParser dataParser = parsers.stream()
                .filter(parser -> parser.supports(resource))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No appropriate parser for: " + resource));

        InputStream stream = this.getClass().getClassLoader()
                .getResourceAsStream(resource);

        if (stream == null) {
            throw new IllegalArgumentException("Resource not found: " + resource);
        }
        return dataParser.parse(stream, elementType);
    }
}
