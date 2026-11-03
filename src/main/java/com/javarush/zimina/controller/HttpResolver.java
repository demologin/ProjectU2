package com.javarush.zimina.controller;

import com.javarush.zimina.cmd.Command;
import com.javarush.zimina.config.Container;
import jakarta.servlet.http.HttpServletRequest;

public class HttpResolver {

    public Command resolve(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String kebabName = uri.substring(uri.lastIndexOf('/') + 1);
        if (kebabName.isEmpty()) {
            kebabName = "home";
        }

        String className = convertKebabStyleToCamelCase(kebabName);
        try {
            String fullName = Command.class.getPackageName() + "." + className;
            Class<?> aClass = Class.forName(fullName);
            return (Command) Container.find(aClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Нет команды для адреса " + uri, e);
        }
    }


    /**
     * Превращает kebab-case в PascalCase: "next-question" -> "NextQuestion".
     */
    private static String convertKebabStyleToCamelCase(String input) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;
        for (char c : input.toCharArray()) {
            if (c == '-') capitalizeNext = true;
            else if (capitalizeNext) {
                result.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                result.append(Character.toLowerCase(c));
            }
        }
        return result.toString();
    }
}
