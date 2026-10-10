package com.javarush.quest.util;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.exception.ErrorMessage;
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

    public ResourceLoader(JsonParser jsonParser, YamlParser yamlParser) {
        parsers.add(jsonParser);
        parsers.add(yamlParser);
    }

    public <T> T load(@NonNull String resource, Class<T> targetType) {
        DataParser parser = findParser(resource);

        try (InputStream stream = mapResourceToStream(resource)) {
            return parser.parse(stream, targetType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public <T> List<T> loadList(@NonNull String resource, Class<T> elementType) {
        DataParser parser = findParser(resource);

        try (InputStream stream = mapResourceToStream(resource)) {
            return parser.parseList(stream, elementType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private DataParser findParser(String resource) {
        return parsers.stream()
                .filter(parser -> parser.canParse(resource))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        ErrorMessage.UNSUPPORTED_FILE + resource));
    }

    private InputStream mapResourceToStream(String resource) {
        return getClass().getClassLoader().getResourceAsStream(resource);
    }
}
