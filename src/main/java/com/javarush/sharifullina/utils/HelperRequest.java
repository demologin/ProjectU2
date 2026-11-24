package com.javarush.sharifullina.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class HelperRequest {
    private HelperRequest() {
        throw new IllegalStateException("Utility class");
    }

    public static void setError(HttpServletRequest request, String error) {
        request.getSession().setAttribute(Constant.ERROR_MESSAGE, error);
    }

    public static Long getId(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        Object userId = session.getAttribute("userId");
        if (userId instanceof Long) {
            return (Long) userId;
        }
        if (userId instanceof Number) {
            return ((Number) userId).longValue(); // Спасет, если положили Integer
        }

        return null;
    }
}
