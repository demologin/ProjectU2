package com.javarush.rukavanov.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class JsonUtil {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T read(InputStream inputStream, TypeReference<T> typeReference) throws IOException {
        return objectMapper.readValue(inputStream, typeReference);
    }

    public void write(Path path, Object data) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(path.toFile(), data);
    }
}
