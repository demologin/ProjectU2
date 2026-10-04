package com.javarush.quest.util;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.util.data.DataParser;
import com.javarush.quest.util.data.JsonParser;
import com.javarush.quest.util.data.YamlParser;
import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class ResourceLoader {
    private final List<DataParser> parsers = new ArrayList<>();
    private static final String PARSING_EXCEPTION = "Cannot parse resource: ";
    private static final String UNSUPPORTED_FILE = "Cannot find appropriate parser for: ";

    public ResourceLoader(JsonParser jsonParser, YamlParser yamlParser) {
        parsers.add(jsonParser);
        parsers.add(yamlParser);
    }

    public <T> T load(@NonNull String resource, Class<T> targetType) {
        DataParser parser = findParser(resource);

        try (InputStream stream = mapResourceToStream(resource)) {
            return parser.parse(stream, targetType);
        } catch (IOException e) {
            throw new RuntimeException(PARSING_EXCEPTION + resource, e);
        }
    }

    public <T> List<T> loadList(@NonNull String resource, Class<T> elementType) {
        DataParser parser = findParser(resource);

        try (InputStream stream = mapResourceToStream(resource)) {
            return parser.parseList(stream, elementType);
        } catch (IOException e) {
            throw new RuntimeException(PARSING_EXCEPTION + resource, e);
        }
    }

    private DataParser findParser(String resource) {
        return parsers.stream()
                .filter(parser -> parser.canParse(resource))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        UNSUPPORTED_FILE + resource));
    }

    private InputStream mapResourceToStream(String resource) {
        return getClass().getClassLoader().getResourceAsStream(resource);
    }
}
