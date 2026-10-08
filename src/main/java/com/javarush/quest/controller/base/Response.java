package com.javarush.quest.controller.base;

import java.util.LinkedHashMap;
import java.util.Map;

public sealed interface Response {
    Response DEFAULT = new Default();

    record Default() implements Response {}

    record Forward(String target, boolean isJsp) implements Response {}

    record Redirect(String target, Map<String, String> queryParams) implements Response {

        public Redirect withParam(String key, String value) {
            queryParams.put(key, value);
            return this;
        }
    }

    static Response forward(String target) {
        return new Forward(target, !target.startsWith("/"));
    }

    static Redirect redirect(String target) {
        return new Redirect(target, new LinkedHashMap<>());
    }
}
