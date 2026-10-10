package com.javarush.quest.controller.base;

import java.util.LinkedHashMap;
import java.util.Map;

public sealed interface Response {
    Response JSP = new JSP();

    record JSP() implements Response {}

    record Forward(String target, boolean isJsp) implements Response {}

    record Redirect(String target, Map<String, String> queryParams) implements Response {

        public Redirect withParam(String key, Object value) {
            queryParams.put(key, value.toString());
            return this;
        }
    }

    //todo check isJsp here or in BaseServlet?
    //check for target.endsWith(".jsp") instead to allow /foo/bar.jsp or foo/bar.jsp ?
    static Response forward(String target) {
        return new Forward(target, !target.startsWith("/"));
    }

    static Redirect redirect(String target) {
        return new Redirect(target, new LinkedHashMap<>());
    }
}
