package com.javarush.khmelov.app.controller;

import com.javarush.khmelov.app.cmd.Command;
import com.javarush.khmelov.app.config.Winter;
import jakarta.servlet.http.HttpServletRequest;

public class HttpResolver {
    public Command resolve(HttpServletRequest req) {
        String uri = req.getRequestURI();
        uri = uri.equals("/") ? "/start-page" : uri;
        uri = uri.split("[/?#]")[1].replace("/", "");
        String simpleName = convertKebabStyleToCamelCase(uri);
        String packageName = Command.class.getPackageName();
        String fqn = packageName + "." + simpleName;
        try {
            Class<?> aClass = Class.forName(fqn);
            return (Command) Winter.getBean(aClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static String convertKebabStyleToCamelCase(String input) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;
        for (char c : input.toCharArray()) {
            if (c == '-') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    result.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }
        return result.toString();
    }
}
