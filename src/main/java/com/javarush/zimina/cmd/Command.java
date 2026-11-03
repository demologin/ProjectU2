package com.javarush.zimina.cmd;

import jakarta.servlet.http.HttpServletRequest;

public interface Command {

    default String doGet(HttpServletRequest request) {
        return getView();
    }

    default String doPost(HttpServletRequest request) {
        return getView();
    }

    default String getView() {
        return toKebabCase(this.getClass().getSimpleName());
    }

    private static String toKebabCase(String name) {
        StringBuilder result = new StringBuilder();
        for (char c : name.toCharArray()) {
            if (Character.isUpperCase(c)) {
                if (!result.isEmpty()) {
                    result.append('-');
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}

