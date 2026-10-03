package com.javarush.quest.util.data;

import java.io.InputStream;
import java.util.List;

public interface DataParser {

    <T> List<T> parse(InputStream stream, Class<T> elementType);

    boolean supports(String resourceName);
}
