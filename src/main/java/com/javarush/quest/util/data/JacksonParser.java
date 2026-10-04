package com.javarush.quest.util.data;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public abstract class JacksonParser implements DataParser {
    protected ObjectMapper mapper;

    @Override
    public <T> T parse(InputStream stream, Class<T> targetType) throws IOException {
        return mapper.readValue(stream, targetType);
    }

    @Override
    public <T> List<T> parseList(InputStream stream, Class<T> elementType) throws IOException {
        JavaType collectionType = mapper.getTypeFactory()
                .constructCollectionType(List.class, elementType);
        return mapper.readValue(stream, collectionType);
    }
}
