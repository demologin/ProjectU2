package com.javarush.quest.util.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface DataParser {

    boolean canParse(String fileName);

    <T> T parse(InputStream stream, Class<T> targetType) throws IOException;

    <T> List<T> parseList(InputStream stream, Class<T> elementType) throws IOException;
}
